package com.hazmelaucb.ms_rating.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingRequestDTO {
    private Long id;
    private Long rating;
    private Long idAnuncio;
    private Long idUsuario;
    private Integer scoreAssigned;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime ratedAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    // Default constructor required for JSON deserialization
    public RatingRequestDTO() {
    }

    // Constructor for creating new ratings (without scoreAssigned)
    public RatingRequestDTO(Long id, Long rating, Long idAnuncio, Long idUsuario,
            LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.idAnuncio = idAnuncio;
        this.idUsuario = idUsuario;
        this.ratedAt = ratedAt;
        this.updatedAt = updatedAt;
    }

    // Constructor for retrieving existing ratings (with scoreAssigned)
    public RatingRequestDTO(Long id, Long rating, Long idAnuncio, Long idUsuario, Integer scoreAssigned,
            LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = rating;
        this.idAnuncio = idAnuncio;
        this.idUsuario = idUsuario;
        this.scoreAssigned = scoreAssigned;
        this.ratedAt = ratedAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters

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
