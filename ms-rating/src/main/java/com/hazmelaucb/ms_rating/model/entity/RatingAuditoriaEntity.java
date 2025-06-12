package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class RatingAuditoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rau")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rating_id", nullable = false)
    private String rating;

    @Column(name = "anuncio_id", nullable = false)
    private String anuncioId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "cambio", length = 500)
    private String cambio;

    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;

    public RatingAuditoriaEntity(LocalDateTime fechaCambio, String cambio, String userId, String anuncioId,
            String rating, Integer id) {
        this.fechaCambio = fechaCambio;
        this.cambio = cambio;
        this.userId = userId;
        this.anuncioId = anuncioId;
        this.rating = rating;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getAnuncioId() {
        return anuncioId;
    }

    public void setAnuncioId(String anuncioId) {
        this.anuncioId = anuncioId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
