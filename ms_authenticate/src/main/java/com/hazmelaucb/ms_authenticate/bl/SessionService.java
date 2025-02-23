package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.ActiveSessionRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.dto.ActiveSessionResponse;
import com.hazmelaucb.ms_authenticate.entity.ActiveSessionEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SessionService {

    private final ActiveSessionRepository activeSessionRepository;
    private final UserRepository userRepository;

    public SessionService(ActiveSessionRepository activeSessionRepository, UserRepository userRepository) {
        this.activeSessionRepository = activeSessionRepository;
        this.userRepository = userRepository;
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

    public void logoutByToken(String refreshToken) {
        Optional<ActiveSessionEntity> sessionOpt = activeSessionRepository.findByRefreshToken(refreshToken);

        if (sessionOpt.isEmpty()) {
            throw new RuntimeException("❌ ERROR: Sesión no encontrada para el refresh token.");
        }

        activeSessionRepository.delete(sessionOpt.get());
        System.out.println("✅ Sesión eliminada correctamente.");
    }

    public void logoutAll(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        activeSessionRepository.deleteByUser(user);
        System.out.println("✅ Todas las sesiones del usuario han sido eliminadas.");
    }

}
