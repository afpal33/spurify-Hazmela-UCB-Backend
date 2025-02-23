package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.bl.SessionService;
import com.hazmelaucb.ms_authenticate.dto.ActiveSessionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ActiveSessionResponse>> getActiveSessions(@PathVariable UUID userId) {
        return ResponseEntity.ok(sessionService.getActiveSessions(userId));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader, HttpServletRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido");
        }

        String refreshToken = authHeader.substring(7); // Remueve "Bearer "

        sessionService.logoutByToken(refreshToken, request); // 🔹 Ahora pasamos también `request`

        return ResponseEntity.ok("✅ Sesión cerrada correctamente.");
    }





    @DeleteMapping("/logout/all/{userId}")
    public ResponseEntity<String> logoutAll(@PathVariable UUID userId) {
        sessionService.logoutAll(userId);
        return ResponseEntity.ok("Todas las sesiones cerradas correctamente.");
    }
}
