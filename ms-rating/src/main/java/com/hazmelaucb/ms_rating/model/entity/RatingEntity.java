package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @Column(name = "id_rating", nullable = false)
    private String idRating;

    @Column(name = "id_anuncio", nullable = false)
    private String idAnuncio;

    @Column(name = "user_id", nullable = false, length = 100)
    private String idUsuario;

    @Column(name = "rating", nullable = false)
    private Long rating;

    @Column(name = "score_assigned", nullable = false)
    private Integer scoreAssigned;

    @Column(name = "rated_at", nullable = false)
    private LocalDateTime ratedAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public RatingEntity(String idRating, String idAnuncio, String idUsuario, Long rating, Integer scoreAssigned,
            LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.idRating = idRating;
        this.idAnuncio = idAnuncio;
        this.idUsuario = idUsuario;
        this.rating = rating;
        this.scoreAssigned = scoreAssigned;
        this.ratedAt = ratedAt;
        this.updatedAt = updatedAt;
    }

    public RatingEntity() {

    }

    public String getIdRating() {
        return idRating;
    }

    public void setIdRating(String idRating) {
        this.idRating = idRating;
    }

    public String getIdAnuncio() {
        return idAnuncio;
    }

    public void setIdAnuncio(String idAnuncio) {
        this.idAnuncio = idAnuncio;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getRating() {
        return Math.toIntExact(rating);
    }

    public void setRating(Long rating) {
        this.rating = rating;
    }

    public Integer getScoreAssigned() {
        return scoreAssigned;
    }

    public void setScoreAssigned(Integer scoreAssigned) {
        this.scoreAssigned = scoreAssigned;
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
