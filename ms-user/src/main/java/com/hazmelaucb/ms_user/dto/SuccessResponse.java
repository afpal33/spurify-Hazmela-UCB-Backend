package com.hazmelaucb.ms_user.dto;


import java.util.UUID;

public class SuccessResponse {
    private final String message;
    private final UUID userId;

    public SuccessResponse(String message, UUID userId) {
        this.message = message;
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public UUID getUserId() {
        return userId;
    }
}
