package com.hazmelaucb.ms_rating.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingRequestDTO {
    private String idRating; // Cambiado de Long a String
    private Long rating;
    private String idAnuncio;
    private String idUsuario;
    private Integer scoreAssigned;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ratedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    // Default constructor required for JSON deserialization
    public RatingRequestDTO() {
    }

    // Constructor para crear nuevos ratings (sin scoreAssigned)
    public RatingRequestDTO(String idRating, Long rating, String idAnuncio, String idUsuario,
            LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.idRating = idRating;
        this.rating = rating;
        this.idAnuncio = idAnuncio;
        this.idUsuario = idUsuario;
    }

    // Constructor para recuperar ratings existentes (con scoreAssigned)
    public RatingRequestDTO(String idRating, Long rating, String idAnuncio, String idUsuario, Integer scoreAssigned,
            LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.idRating = idRating;
        this.rating = rating;
        this.idAnuncio = idAnuncio;
        this.idUsuario = idUsuario;
        this.scoreAssigned = scoreAssigned;
        this.ratedAt = ratedAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters

    public String getIdRating() {
        return idRating;
    }

    public void setIdRating(String idRating) {
        this.idRating = idRating;
    }

    public Long getRating() {
        return rating;
    }

    public void setRating(Long rating) {
        this.rating = rating;
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
