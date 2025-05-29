package com.hazmelaucb.ms_report.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Schema(description = "Objeto de transferencia de datos para reportes de anuncios y perfiles")
public class ReportDTO {
    private Long id;

    @NotBlank(message = "El tipo de reporte es obligatorio")
    @Schema(description = "Tipo de reporte (anuncio o perfil)", example = "anuncio")
    private String tipo;

    @NotBlank(message = "La descripción es obligatoria")
    @Schema(description = "Descripción del motivo del reporte", example = "Contenido inapropiado")
    private String descripcion;

    @Schema(description = "Fecha y hora en que se realizó el reporte")
    private LocalDateTime fecha;

    // Constructores, getters y setters

    public ReportDTO() {}

    public ReportDTO(Long id, String tipo, String descripcion, LocalDateTime fecha) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
