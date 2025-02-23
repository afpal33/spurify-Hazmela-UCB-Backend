package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.bl.LoginAttemptService;
import com.hazmelaucb.ms_authenticate.dto.LoginAttemptResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/login-attempts")
public class LoginAttemptController {

    private final LoginAttemptService loginAttemptService;

    public LoginAttemptController(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<LoginAttemptResponse>> getLoginAttempts(@PathVariable UUID userId) {
        return ResponseEntity.ok(loginAttemptService.getLoginAttempts(userId));
    }
}
