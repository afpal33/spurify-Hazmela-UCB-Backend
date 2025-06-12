package com.hazmelaucb.ms_authenticate.dto;

public class RegisterResponse {
    private String message;
    private String uid;

    public RegisterResponse(String message, String uid) {
        this.message = message;
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public String getUid() {
        return uid;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
