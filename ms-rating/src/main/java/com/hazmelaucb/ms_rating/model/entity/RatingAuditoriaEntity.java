package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

public class RatingAuditoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rau")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "rating_id", nullable = false)
    private RatingEntity rating;

    @ManyToOne
    @JoinColumn(name = "anuncio_id", nullable = false)
    private AnuncioEntity anuncio;

    @Column(name = "user_id")
    private Integer userId;

    private String cambio;

    @Column(name = "fecha_cambio", nullable = false)
    private ZonedDateTime fechaCambio;

    public RatingAuditoriaEntity(Integer id, RatingEntity rating, AnuncioEntity anuncio, Integer userId, String cambio, ZonedDateTime fechaCambio) {
        this.id = id;
        this.rating = rating;
        this.anuncio = anuncio;
        this.userId = userId;
        this.cambio = cambio;
        this.fechaCambio = fechaCambio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }

    public AnuncioEntity getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioEntity anuncio) {
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
