package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rating")
    private Long idRating;

    @Column(name = "id_anuncio", nullable = false)
    private Long idAnuncio;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "rated_at", nullable = false)
    private LocalDateTime ratedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public RatingEntity(Long idRating, Long idAnuncio, Integer rating, LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.idRating = idRating;
        this.idAnuncio = idAnuncio;
        this.rating = rating;
        this.ratedAt = ratedAt;
        this.updatedAt = updatedAt;
    }

    public RatingEntity() {

    }

    public Long getIdRating() {
        return idRating;
    }

    public void setIdRating(Long idRating) {
        this.idRating = idRating;
    }

    public Long getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(Long idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(LocalDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
