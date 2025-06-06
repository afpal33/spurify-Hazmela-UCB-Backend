package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.dao.UserRepository;
import com.hazmelaucb.ms_authenticate.entity.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "API para la administración de usuarios")
public class UnlockUserController {

    private final UserRepository userRepository;

    public UnlockUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "${api.admin.unlockUser.description}", description = "${api.admin.unlockUser.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("/unlock-user/{email}")
    public ResponseEntity<String> unlockUser(
            @Parameter(description = "Correo electrónico del usuario a desbloquear", required = true)
            @PathVariable String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.resetFailedAttempts();
        userRepository.save(user);

        return ResponseEntity.ok("✅ Usuario desbloqueado exitosamente.");
    }
}
