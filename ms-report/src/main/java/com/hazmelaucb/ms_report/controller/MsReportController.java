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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hazmelaucb.ms_report.dto.ReportDTO;
import com.hazmelaucb.ms_report.service.MsReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/reportes")
@Tag(name = "Reportes", description = "Operaciones para la gestión de reportes de anuncios y perfiles")
public class MsReportController {

    @Autowired
    private MsReportService reportService;

    @GetMapping("/listar")
    @Operation(summary = "Listar todos los reportes", description = "Retorna una lista de todos los reportes registrados.")
    public ResponseEntity<List<ReportDTO>> listarReportes() {
        return ResponseEntity.ok(reportService.listarReportes());
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar un reporte por ID", description = "Busca un reporte utilizando su ID.")
    public ResponseEntity<?> buscarReportePorId(@RequestParam Long id) {
        Optional<ReportDTO> reporte = reportService.buscarPorId(id);
        return reporte.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/agregar")
    @Operation(summary = "Agregar un nuevo reporte", description = "Crea un nuevo reporte en el sistema.")
    public ResponseEntity<?> agregarReporte(@Valid @RequestBody ReportDTO nuevoReporte) {
        try {
            ReportDTO reporteCreado = reportService.agregarReporte(nuevoReporte);
            return ResponseEntity.status(HttpStatus.CREATED).body(reporteCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Error al agregar el reporte: " + e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    @Operation(summary = "Eliminar un reporte", description = "Elimina un reporte por su ID.")
    public ResponseEntity<String> eliminarReporte(@PathVariable Long id) {
        if (reportService.eliminarReporte(id)) {
            return ResponseEntity.ok("Reporte eliminado exitosamente.");
        }
        return ResponseEntity.notFound().build();
    }
}
