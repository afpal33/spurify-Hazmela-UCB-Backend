package com.hazmelaucb.ms_report.dto;
public class ReporteDTO {

    private String tipo;
    private String descripcion;
    private Long idUsuarioReportado;
    private Long idElementoReportado;

    // Constructor
    public ReporteDTO(String tipo, String descripcion, Long idUsuarioReportado, Long idElementoReportado) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.idUsuarioReportado = idUsuarioReportado;
        this.idElementoReportado = idElementoReportado;
    }

    // Getters y Setters
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

    public Long getIdUsuarioReportado() {
        return idUsuarioReportado;
    }

    public void setIdUsuarioReportado(Long idUsuarioReportado) {
        this.idUsuarioReportado = idUsuarioReportado;
    }

    public Long getIdElementoReportado() {
        return idElementoReportado;
    }

    public void setIdElementoReportado(Long idElementoReportado) {
        this.idElementoReportado = idElementoReportado;
    }
}
