package com.hazmelaucb.ms_user.api;

import com.hazmelaucb.ms_user.bl.UserCommonBL;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User", description = "API para la gestión de usuarios comunes en la red social")
public class UserCommonController {

    @Autowired
    private UserCommonBL userCommonBL;

    @Operation(summary = "${api.user.disable.description}", description = "${api.user.disable.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.noContent.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableUser(
            @Parameter(description = "ID del usuario a deshabilitar", required = true)
            @PathVariable("id") UUID userId) {
        userCommonBL.disableUser(userId);
        return ResponseEntity.noContent().build();
    }
}
