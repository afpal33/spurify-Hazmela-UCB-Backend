package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.bl.SessionService;
import com.hazmelaucb.ms_authenticate.dto.ActiveSessionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sessions")
@Tag(name = "Sessions", description = "API para la gestión de sesiones activas de los usuarios")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Operation(summary = "${api.sessions.getActive.description}", description = "${api.sessions.getActive.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<ActiveSessionResponse>> getActiveSessions(
            @Parameter(description = "ID del usuario para obtener sus sesiones activas", required = true)
            @PathVariable UUID userId) {
        return ResponseEntity.ok(sessionService.getActiveSessions(userId));
    }

    @Operation(summary = "${api.sessions.logout.description}", description = "${api.sessions.logout.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "401", description = "${api.responseCodes.unauthorized.description}")
    })
    @DeleteMapping("/logout")
    public ResponseEntity<String> logout(
            @Parameter(description = "Token de autenticación del usuario", required = true)
            @RequestHeader("Authorization") String authHeader, HttpServletRequest request) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Token inválido");
        }

        String refreshToken = authHeader.substring(7); // Remueve "Bearer "

        sessionService.logoutByToken(refreshToken, request); // 🔹 Ahora pasamos también `request`

        return ResponseEntity.ok("✅ Sesión cerrada correctamente.");
    }

    @Operation(summary = "${api.sessions.logoutAll.description}", description = "${api.sessions.logoutAll.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @DeleteMapping("/logout/all/{userId}")
    public ResponseEntity<String> logoutAll(
            @Parameter(description = "ID del usuario para cerrar todas sus sesiones", required = true)
            @PathVariable UUID userId) {
        sessionService.logoutAll(userId);
        return ResponseEntity.ok("Todas las sesiones cerradas correctamente.");
    }
}
