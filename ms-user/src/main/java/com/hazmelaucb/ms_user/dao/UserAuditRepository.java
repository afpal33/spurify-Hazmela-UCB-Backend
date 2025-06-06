package com.hazmelaucb.ms_user.dao;


import com.hazmelaucb.ms_user.entity.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
    // Este repositorio se utilizará para registrar las acciones de auditoría.
}
