package com.hazmelaucb.ms_report.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hazmelaucb.ms_report.dto.ReportDTO;
import com.hazmelaucb.ms_report.service.MsReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reportes", description = "Operaciones para la gestión de reportes de anuncios y perfiles")
public class MsReportController {

    @Autowired
    private MsReportService reportService;

    @PostMapping
    @Operation(summary = "Crear un nuevo reporte", description = "Crea un nuevo reporte sobre un anuncio o usuario.")
    public ResponseEntity<?> crearReporte(@Valid @RequestBody ReportDTO nuevoReporte) {
        try {
            ReportDTO reporteCreado = reportService.agregarReporte(nuevoReporte);
            return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al agregar el reporte: " + e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener reportes por usuario", description = "Obtiene todos los reportes realizados por un usuario específico.")
    public ResponseEntity<List<ReportDTO>> obtenerReportesPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(reportService.buscarPorUserId(userId));
    }

    @GetMapping("/{reportId}")
    @Operation(summary = "Obtener reporte por ID", description = "Obtiene un reporte específico por su ID.")
    public ResponseEntity<?> obtenerReportePorId(@PathVariable Long reportId) {
        Optional<ReportDTO> reporte = reportService.buscarPorId(reportId);
        return reporte.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{reportId}/delete")
    @Operation(summary = "Eliminar reporte por ID", description = "Elimina un reporte específico por su ID.")
    public ResponseEntity<String> eliminarReporte(@PathVariable Long reportId) {
        if (reportService.eliminarReporte(reportId)) {
            return ResponseEntity.ok("Reporte eliminado exitosamente.");
        }
        return ResponseEntity.notFound().build();
    }
}
