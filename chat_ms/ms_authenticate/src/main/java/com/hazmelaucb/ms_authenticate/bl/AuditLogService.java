package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.AuditLogRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.entity.AuditLogEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import com.hazmelaucb.ms_authenticate.utils.exceptions.InvalidAuditLogException;
import com.hazmelaucb.ms_authenticate.utils.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;

    public AuditLogService(AuditLogRepository auditLogRepository, UserRepository userRepository) {
        this.auditLogRepository = auditLogRepository;
        this.userRepository = userRepository;
    }

    public void registerAuditLog(UserEntity user, String action, String ip, String userAgent) {

        if (user == null) {
            throw new InvalidAuditLogException("El usuario no puede ser nulo al registrar un log de auditoría.");
        }
        if (!StringUtils.hasText(action)) {
            throw new InvalidAuditLogException("La acción del log no puede estar vacía.");
        }
        if (!StringUtils.hasText(ip)) {
            throw new InvalidAuditLogException("La dirección IP no puede estar vacía.");
        }
        if (!StringUtils.hasText(userAgent)) {
            throw new InvalidAuditLogException("El User-Agent no puede estar vacío.");
        }

        AuditLogEntity log = new AuditLogEntity();
        log.setUser(user);
        log.setAction(action);
        log.setIp(ip);
        log.setUserAgent(userAgent);
        auditLogRepository.save(log);
    }

    public List<AuditLogEntity> getUserAuditLogs(UUID userId) {

        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo.");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return auditLogRepository.findByUserOrderByTimestampDesc(user);
    }
}
