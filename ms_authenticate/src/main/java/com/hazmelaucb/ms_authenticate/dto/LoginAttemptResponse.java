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
}
