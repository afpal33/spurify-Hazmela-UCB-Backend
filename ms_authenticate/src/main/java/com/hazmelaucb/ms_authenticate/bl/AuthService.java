package com.hazmelaucb.ms_authenticate.bl;


import com.hazmelaucb.ms_authenticate.dao.ActiveSessionRepository;
import com.hazmelaucb.ms_authenticate.dao.RoleRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.AuthRequest;
import com.hazmelaucb.ms_authenticate.dto.AuthResponse;
import com.hazmelaucb.ms_authenticate.dto.RegisterRequest;
import com.hazmelaucb.ms_authenticate.entity.ActiveSessionEntity;
import com.hazmelaucb.ms_authenticate.entity.RoleEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import com.hazmelaucb.ms_authenticate.security.JwtTokenProvider;
import com.hazmelaucb.ms_authenticate.utils.exceptions.InvalidCredentialsException;
import com.hazmelaucb.ms_authenticate.utils.exceptions.TokenInvalidException;
import com.hazmelaucb.ms_authenticate.utils.exceptions.UserAlreadyExistsException;
import com.hazmelaucb.ms_authenticate.utils.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;


import java.sql.Timestamp;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginAttemptService loginAttemptService;
    private final ActiveSessionRepository activeSessionRepository;
    private final RevokedTokenService revokedTokenService;
    private final AuditLogService auditLogService;
    private final RoleRepository roleRepository;


    public AuthService(UserRepository userRepository,
                       JwtTokenProvider jwtTokenProvider,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       LoginAttemptService loginAttemptService,
                       ActiveSessionRepository activeSessionRepository,
                       RevokedTokenService revokedTokenService,
                       AuditLogService auditLogService,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.loginAttemptService = loginAttemptService;
        this.activeSessionRepository = activeSessionRepository;
        this.revokedTokenService = revokedTokenService;
        this.auditLogService = auditLogService;
        this.roleRepository = roleRepository;
    }


    @Transactional
    public AuthResponse login(AuthRequest request, HttpServletRequest httpRequest) {
        String ip = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        if (user.isLocked()) {
            throw new InvalidCredentialsException("Tu cuenta está bloqueada por intentos fallidos.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getHashedPassword())) {
            user.registerFailedAttempt();
            userRepository.save(user);
            loginAttemptService.registerLoginAttempt(user, false, ip, userAgent);
            throw new InvalidCredentialsException("Contraseña incorrecta. Intentos fallidos: " + user.getFailedAttempts());
        }

        // Restablecer intentos fallidos y desbloquear usuario si es necesario
        user.resetFailedAttempts();
        user.updateLastLogin(ip, userAgent);
        userRepository.save(user);

        loginAttemptService.registerLoginAttempt(user, true, ip, userAgent);

        // 🔹 Registrar evento de auditoría
        auditLogService.registerAuditLog(user, "LOGIN", ip, userAgent);

        String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());

        // Eliminar sesiones activas previas y registrar nueva sesión
        activeSessionRepository.deleteByUser(user);

        ActiveSessionEntity session = new ActiveSessionEntity();
        session.setUser(user);
        session.setRefreshToken(refreshToken);
        session.setIp(ip);
        session.setUserAgent(userAgent);
        session.setExpiryDate(new Timestamp(System.currentTimeMillis() + 604800000));

        activeSessionRepository.save(session);

        return new AuthResponse(accessToken, refreshToken);
    }



    public void register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("El usuario ya existe");
        }

        UserEntity newUser = new UserEntity();
        newUser.setEmail(request.getEmail());
        newUser.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setAuthMethod("EMAIL");

        // 🔹 Asignar rol "ESTUDIANTE" por defecto
        RoleEntity estudianteRole = roleRepository.findByName("ESTUDIANTE")
                .orElseThrow(() -> new RuntimeException("Rol 'ESTUDIANTE' no encontrado"));
        newUser.getRoles().add(estudianteRole);

        userRepository.save(newUser);
    }


    public AuthResponse refreshAccessToken(String refreshToken) {
        System.out.println("🔄 Intentando refrescar el token...");

        Optional<ActiveSessionEntity> sessionOpt = activeSessionRepository.findByRefreshToken(refreshToken);

        if (sessionOpt.isEmpty()) {
            System.out.println("❌ ERROR: Sesión no encontrada en la base de datos para el refresh token.");
            throw new TokenInvalidException("Refresh token inválido o sesión no encontrada");
        }

        ActiveSessionEntity session = sessionOpt.get();
        UserEntity user = session.getUser();

        if (user == null) {
            System.out.println("❌ ERROR: No se encontró un usuario asociado a la sesión.");
            throw new UserNotFoundException("Usuario no encontrado para este refresh token");
        }

        System.out.println("✅ Usuario encontrado: " + user.getEmail());

        String newAccessToken = jwtTokenProvider.generateToken(user);

        return new AuthResponse(newAccessToken, refreshToken);
    }



}
