package com.hazmelaucb.ms_authenticate.dao;

import com.hazmelaucb.ms_authenticate.entity.AuditLogEntity;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLogEntity, UUID> {
    List<AuditLogEntity> findByUserOrderByTimestampDesc(UserEntity user);
}
