package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.ActiveSessionRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.ActiveSessionResponse;
import com.hazmelaucb.ms_authenticate.entity.ActiveSessionEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import com.hazmelaucb.ms_authenticate.utils.exceptions.InvalidTokenException;
import com.hazmelaucb.ms_authenticate.utils.exceptions.SessionNotFoundException;
import com.hazmelaucb.ms_authenticate.utils.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final ActiveSessionRepository activeSessionRepository;
    private final UserRepository userRepository;
    private final RevokedTokenService revokedTokenService;
    private final AuditLogService auditLogService;

    public SessionService(ActiveSessionRepository activeSessionRepository, UserRepository userRepository, RevokedTokenService revokedTokenService,
                          AuditLogService auditLogService) {
        this.activeSessionRepository = activeSessionRepository;
        this.userRepository = userRepository;
        this.revokedTokenService = revokedTokenService;
        this.auditLogService = auditLogService;
    }

    public List<ActiveSessionResponse> getActiveSessions(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return activeSessionRepository.findByUser(user).stream()
                .map(session -> new ActiveSessionResponse(
                        session.getId(),
                        user.getId(),
                        session.getIp(),
                        session.getUserAgent(),
                        session.getCreatedAt(),
                        session.getExpiryDate()
                )).collect(Collectors.toList());
    }

    public void logoutByToken(String refreshToken, HttpServletRequest request) {

        if (!StringUtils.hasText(refreshToken)) {
            throw new InvalidTokenException("El token no puede estar vacío o nulo.");
        }

        Optional<ActiveSessionEntity> sessionOpt = activeSessionRepository.findByRefreshToken(refreshToken);

        if (sessionOpt.isEmpty()) {
            throw new SessionNotFoundException("Sesión no encontrada para este refresh token.");
        }

        ActiveSessionEntity session = sessionOpt.get();
        UserEntity user = session.getUser();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        // 🔹 Revocar el refreshToken antes de eliminar la sesión
        if (!revokedTokenService.isTokenRevoked(refreshToken)) {
            revokedTokenService.revokeToken(refreshToken);
        }

        // 🔹 Registrar evento de auditoría
        auditLogService.registerAuditLog(user, "LOGOUT", ip, userAgent);

        // 🔹 Eliminar la sesión de la base de datos
        activeSessionRepository.delete(session);
    }


    public void logoutAll(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        activeSessionRepository.deleteByUser(user);
        System.out.println("✅ Todas las sesiones del usuario han sido eliminadas.");
    }

}
