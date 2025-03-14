package com.hazmelaucb.ms_rating.model.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class RatingRequestDTO {
    private Long id;
    private Long rating;
    private Long idAnuncio;
    private Long idUsuario;
    private ZonedDateTime ratedAt;
    private ZonedDateTime updatedAt;

    public RatingRequestDTO(Long id, Long rating, Long idAnuncio, Long idUsuario, ZonedDateTime ratedAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.idAnuncio = idAnuncio;
        this.idUsuario = idUsuario;
        this.ratedAt = ratedAt;
        this.updatedAt = updatedAt;
    }

    public RatingRequestDTO(Long idRating, Integer rating, Long idAnuncio, Long idUsuario, LocalDateTime ratedAt, LocalDateTime updatedAt) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Long getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Long idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ZonedDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(ZonedDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
