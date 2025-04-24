package com.hazmelaucb.ms_authenticate.utils.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }
}
