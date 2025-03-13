package com.hazmelaucb.ms_authenticate.dto;

import java.sql.Timestamp;
import java.util.UUID;

public class LoginAttemptResponse {
    private UUID id;
    private boolean success;
    private String ip;
    private String userAgent;
    private Timestamp timestamp;

    public LoginAttemptResponse(UUID id, boolean success, String ip, String userAgent, Timestamp timestamp) {
        this.id = id;
        this.success = success;
        this.ip = ip;
        this.userAgent = userAgent;
        this.timestamp = timestamp;
    }

    // Getters y Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
