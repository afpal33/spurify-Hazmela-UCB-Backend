package com.hazmelaucb.ms_user.api;

import com.hazmelaucb.ms_user.bl.AdministratorBL;
import com.hazmelaucb.ms_user.dto.AdministratorDTO;
import com.hazmelaucb.ms_user.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admins")
@Tag(name = "Administrator", description = "API para la gestión de administradores en la red social")
public class AdministratorController {

    @Autowired
    private AdministratorBL administratorBL;

    @Operation(summary = "${api.administrator.create.description}", description = "${api.administrator.create.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "409", description = "${api.responseCodes.conflict.description}")
    })
    @PostMapping
    public ResponseEntity<AdministratorDTO> createAdministrator(@RequestBody AdministratorDTO adminDTO) {
        AdministratorDTO created = administratorBL.createAdministrator(adminDTO);
        return ResponseEntity.status(201).body(created);
    }

    @Operation(summary = "${api.administrator.update.description}", description = "${api.administrator.update.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(
            @Parameter(description = "ID del administrador a actualizar", required = true)
            @PathVariable("id") UUID userId,
            @RequestBody AdministratorDTO adminDTO) {
        AdministratorDTO updated = administratorBL.updateAdministrator(userId, adminDTO);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "${api.administrator.delete.description}", description = "${api.administrator.delete.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.noContent.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "422", description = "${api.responseCodes.unprocessableEntity.description}")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteAdministrator(
            @Parameter(description = "ID del administrador a eliminar", required = true)
            @PathVariable("id") UUID userId) {

        SuccessResponse response = administratorBL.deleteAdministrator(userId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "${api.administrator.getById.description}", description = "${api.administrator.getById.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(
            @Parameter(description = "ID del administrador a obtener", required = true)
            @PathVariable("id") UUID userId) {
        AdministratorDTO dto = administratorBL.getAdministratorById(userId);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "${api.administrator.getAll.description}", description = "${api.administrator.getAll.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor")
    })
    @GetMapping
    public ResponseEntity<List<AdministratorDTO>> getAllAdministrators() {
        List<AdministratorDTO> dtos = administratorBL.getAllAdministrators();
        return ResponseEntity.ok(dtos);
    }
}
