package com.hazmelaucb.ms_authenticate.bl;


import com.hazmelaucb.ms_authenticate.dao.ActiveSessionRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.AuthRequest;
import com.hazmelaucb.ms_authenticate.dto.AuthResponse;
import com.hazmelaucb.ms_authenticate.dto.RegisterRequest;
import com.hazmelaucb.ms_authenticate.entity.ActiveSessionEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import com.hazmelaucb.ms_authenticate.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;


import java.sql.Timestamp;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ActiveSessionRepository activeSessionRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    public AuthService(UserRepository userRepository, ActiveSessionRepository activeSessionRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.activeSessionRepository = activeSessionRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse login(AuthRequest request, HttpServletRequest httpRequest) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getHashedPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        String accessToken = jwtTokenProvider.generateToken(user.getEmail());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        // Obtener información del cliente
        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");

        // Guardar sesión en la base de datos
        ActiveSessionEntity session = new ActiveSessionEntity();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setIp(ip);
        session.setUserAgent(userAgent);
        session.setExpiryDate(new Timestamp(System.currentTimeMillis() + 604800000)); // 7 días

        activeSessionRepository.save(session);

        return new AuthResponse(accessToken, refreshToken);
    }

    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El usuario ya existe");
        }

        UserEntity newUser = new UserEntity();
        newUser.setEmail(request.getEmail());
        newUser.setHashedPassword(passwordEncoder.encode(request.getPassword())); // Se cifra la contraseña
        newUser.setAuthMethod("EMAIL");
        userRepository.save(newUser);
    }

    public AuthResponse refreshAccessToken(String refreshToken) {
        System.out.println("🔄 Intentando refrescar el token...");

        Optional<ActiveSessionEntity> sessionOpt = activeSessionRepository.findByRefreshToken(refreshToken);

        if (sessionOpt.isEmpty()) {
            System.out.println("❌ ERROR: Sesión no encontrada en la base de datos para el refresh token.");
            throw new RuntimeException("Refresh token inválido o sesión no encontrada");
        }

        ActiveSessionEntity session = sessionOpt.get();
        UserEntity user = session.getUser();

        if (user == null) {
            System.out.println("❌ ERROR: No se encontró un usuario asociado a la sesión.");
            throw new RuntimeException("Usuario no encontrado para este refresh token");
        }

        System.out.println("✅ Usuario encontrado: " + user.getEmail());

        String newAccessToken = jwtTokenProvider.generateToken(user.getEmail());

        return new AuthResponse(newAccessToken, refreshToken);
    }


}
