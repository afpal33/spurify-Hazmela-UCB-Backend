package com.hazmelaucb.ms_user.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "user_audit")
public class UserAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id")
    private Long auditId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "action", length = 50, nullable = false)
    private String action; // 'CREATE', 'UPDATE' o 'DELETE'

    @Column(name = "action_date", nullable = false)
    private LocalDateTime actionDate = LocalDateTime.now();

    @Column(name = "previous_data", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String previousData;

    @Column(name = "device_address", length = 100, nullable = false)
    private String deviceAddress;

    @Column(name = "ip_address", length = 100, nullable = false)
    private String ipAddress;

    // Getters y setters

    public Long getAuditId() {
        return auditId;
    }
    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public LocalDateTime getActionDate() {
        return actionDate;
    }
    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }
    public String getPreviousData() {
        return previousData;
    }
    public void setPreviousData(String previousData) {
        this.previousData = previousData;
    }
    public String getDeviceAddress() {
        return deviceAddress;
    }
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }
    public String getIpAddress() {
        return ipAddress;
    }
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
