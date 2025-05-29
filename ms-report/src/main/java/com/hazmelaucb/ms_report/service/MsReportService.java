package com.hazmelaucb.ms_report.service;

import com.hazmelaucb.ms_report.model.Report;
import com.hazmelaucb.ms_report.dto.ReportDTO;
import com.hazmelaucb.ms_report.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;

@Service
@Schema(description = "Servicio para la gestión de reportes de anuncios y perfiles")
public class MsReportService {

    private final ReportRepository reportRepository;

    public MsReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // Método para convertir entidad a DTO
    private ReportDTO toDTO(Report report) {
        return new ReportDTO(
                report.getId(),
                report.getTipo(),
                report.getDescripcion(),
                report.getFecha()
        );
    }

    // Método para convertir DTO a entidad
    private Report toEntity(ReportDTO dto) {
        return new Report(
                dto.getId(),
                dto.getTipo(),
                dto.getDescripcion(),
                dto.getFecha()
        );
    }

    // Listar todos los reportes
    public List<ReportDTO> listarReportes() {
        return reportRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar reporte por ID
    public Optional<ReportDTO> buscarPorId(Long id) {
        return reportRepository.findById(id)
                .map(this::toDTO);
    }

    // Buscar reportes por tipo
    public List<ReportDTO> buscarPorTipo(String tipo) {
        return reportRepository.findAll().stream()
                .filter(r -> r.getTipo().equalsIgnoreCase(tipo))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Agregar un nuevo reporte
    public ReportDTO agregarReporte(ReportDTO nuevoReporte) {
        Report report = toEntity(nuevoReporte);
        Report saved = reportRepository.save(report);
        return toDTO(saved);
    }

    // Eliminar un reporte
    public boolean eliminarReporte(Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}