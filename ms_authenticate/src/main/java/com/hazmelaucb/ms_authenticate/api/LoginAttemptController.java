package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.bl.LoginAttemptService;
import com.hazmelaucb.ms_authenticate.dto.LoginAttemptResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/login-attempts")
@Tag(name = "Login Attempts", description = "API para el seguimiento de intentos de inicio de sesión")
public class LoginAttemptController {

    private final LoginAttemptService loginAttemptService;

    public LoginAttemptController(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Operation(summary = "${api.loginAttempt.get.description}", description = "${api.loginAttempt.get.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<LoginAttemptResponse>> getLoginAttempts(
            @Parameter(description = "ID del usuario para obtener intentos de inicio de sesión", required = true)
            @PathVariable UUID userId) {
        return ResponseEntity.ok(loginAttemptService.getLoginAttempts(userId));
    }
}
