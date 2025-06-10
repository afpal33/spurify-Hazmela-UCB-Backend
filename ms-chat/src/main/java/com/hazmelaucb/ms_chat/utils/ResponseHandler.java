package com.hazmelaucb.ms_chat.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value()); // Asegura que aquí sea un número
        response.put("data", data);
        return new ResponseEntity<>(response, status);
    }
}
