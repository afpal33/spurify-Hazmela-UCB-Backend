package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.ActiveSessionRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.ActiveSessionResponse;
import com.hazmelaucb.ms_authenticate.entity.ActiveSessionEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

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
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

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
        Optional<ActiveSessionEntity> sessionOpt = activeSessionRepository.findByRefreshToken(refreshToken);

        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("Sesión no encontrada para este refresh token.");
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
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        activeSessionRepository.deleteByUser(user);
        System.out.println("✅ Todas las sesiones del usuario han sido eliminadas.");
    }

}
