package com.hazmelaucb.ms_authenticate.bl;

import com.hazmelaucb.ms_authenticate.dao.AuditLogRepository;
import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.entity.AuditLogEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.stereotype.Service;

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
        AuditLogEntity log = new AuditLogEntity();
        log.setUser(user);
        log.setAction(action);
        log.setIp(ip);
        log.setUserAgent(userAgent);
        auditLogRepository.save(log);
    }

    public List<AuditLogEntity> getUserAuditLogs(UUID userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return auditLogRepository.findByUserOrderByTimestampDesc(user);
    }
}
