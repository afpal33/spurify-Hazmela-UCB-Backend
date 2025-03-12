package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

public class RatingAuditoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rau")
    private Integer id;

    @Column(name = "rating_id", nullable = false)
    private Integer rating;

    @Column(name = "anuncio_id", nullable = false)
    private Integer anuncio;

    @Column(name = "user_id")
    private Integer userId;

    private String cambio;

    @Column(name = "fecha_cambio", nullable = false)
    private ZonedDateTime fechaCambio;

    public RatingAuditoriaEntity(ZonedDateTime fechaCambio, String cambio, Integer userId, Integer anuncio, Integer rating, Integer id) {
        this.fechaCambio = fechaCambio;
        this.cambio = cambio;
        this.userId = userId;
        this.anuncio = anuncio;
        this.rating = rating;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Integer anuncio) {
        this.anuncio = anuncio;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCambio() {
        return cambio;
    }

    public void setCambio(String cambio) {
        this.cambio = cambio;
    }

    public ZonedDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(ZonedDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
}
