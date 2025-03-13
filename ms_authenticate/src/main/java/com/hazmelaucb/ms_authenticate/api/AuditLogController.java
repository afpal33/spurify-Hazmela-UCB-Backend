package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.bl.AuditLogService;
import com.hazmelaucb.ms_authenticate.entity.AuditLogEntity;
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
@RequestMapping("/audit-logs")
@Tag(name = "Audit Logs", description = "API para la auditoría de acciones realizadas por los usuarios")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Operation(summary = "${api.auditLogs.get.description}", description = "${api.auditLogs.get.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<List<AuditLogEntity>> getUserAuditLogs(
            @Parameter(description = "ID del usuario para obtener sus registros de auditoría", required = true)
            @PathVariable UUID userId) {
        return ResponseEntity.ok(auditLogService.getUserAuditLogs(userId));
    }
}
