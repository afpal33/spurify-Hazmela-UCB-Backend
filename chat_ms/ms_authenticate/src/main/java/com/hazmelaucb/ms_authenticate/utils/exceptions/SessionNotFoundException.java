package com.hazmelaucb.ms_authenticate.utils.exceptions;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException(String message) {
        super(message);
    }
}
