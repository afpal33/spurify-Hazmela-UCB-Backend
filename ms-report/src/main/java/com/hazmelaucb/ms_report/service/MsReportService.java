package com.hazmelaucb.ms_report.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hazmelaucb.ms_report.dto.ReportDTO;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Servicio para la gestión de reportes de anuncios y perfiles")
public class MsReportService {

    private final List<ReportDTO> reportes = new ArrayList<>();

    // Constructor para inicializar datos ficticios de prueba
    public MsReportService() {
        reportes.add(new ReportDTO(1, "anuncio", "Anuncio con contenido inapropiado", LocalDateTime.now().minusDays(1)));
        reportes.add(new ReportDTO(2, "perfil", "Perfil que incumple normas de conducta", LocalDateTime.now()));
    }

    public List<ReportDTO> listarReportes() {
        return reportes;
    }

    public Optional<ReportDTO> buscarPorId(int id) {
        return reportes.stream()
                .filter(r -> r.getId() == id)
                .findFirst();
    }

    public List<ReportDTO> buscarPorTipo(String tipo) {
        return reportes.stream()
                .filter(r -> r.getTipo().equalsIgnoreCase(tipo))
                .collect(Collectors.toList());
    }

    public ReportDTO agregarReporte(ReportDTO nuevoReporte) {
        nuevoReporte.setId(reportes.size() + 1);
        reportes.add(nuevoReporte);
        return nuevoReporte;
    }

    public boolean eliminarReporte(int id) {
        return reportes.removeIf(r -> r.getId() == id);
    }
}
