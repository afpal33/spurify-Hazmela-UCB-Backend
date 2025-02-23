package com.hazmelaucb.ms_authenticate.api;

import com.hazmelaucb.ms_authenticate.bl.AuditLogService;
import com.hazmelaucb.ms_authenticate.entity.AuditLogEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogController {

    private final AuditLogService auditLogService;

    public AuditLogController(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<AuditLogEntity>> getUserAuditLogs(@PathVariable UUID userId) {
        return ResponseEntity.ok(auditLogService.getUserAuditLogs(userId));
    }
}
