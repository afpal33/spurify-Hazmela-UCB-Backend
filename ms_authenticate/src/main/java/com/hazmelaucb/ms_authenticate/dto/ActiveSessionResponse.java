package com.hazmelaucb.ms_authenticate.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class ActiveSessionResponse {
    private UUID sessionId;
    private UUID userId;
    private String ip;
    private String userAgent;
    private Timestamp createdAt;
    private Timestamp expiryDate;

    public ActiveSessionResponse(UUID sessionId, UUID userId, String ip, String userAgent, Timestamp createdAt, Timestamp expiryDate) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.ip = ip;
        this.userAgent = userAgent;
        this.createdAt = createdAt;
        this.expiryDate = expiryDate;
    }

    // Getters y Setters


    public UUID getSessionId() {
        return sessionId;
    }

    public void setSessionId(UUID sessionId) {
        this.sessionId = sessionId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
}
