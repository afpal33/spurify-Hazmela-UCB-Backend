package com.hazmelaucb.ms_report.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // "anuncio" o "perfil"
    private String descripcion;
    private Long idUsuarioReportado;
    private Long idElementoReportado;

    // Constructor por defecto
    public Reporte() {}

    // Constructor con parámetros
    public Reporte(String tipo, String descripcion, Long idUsuarioReportado, Long idElementoReportado) {
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.idUsuarioReportado = idUsuarioReportado;
        this.idElementoReportado = idElementoReportado;
    }

    // Getters y Setters
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
