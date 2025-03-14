package com.hazmelaucb.ms_rating.model.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class RatingRequestDTO {
    private Long id;
    private Long rating;
    private Long idAnuncio;
    private ZonedDateTime ratedAt;
    private ZonedDateTime updatedAt;

    public RatingRequestDTO(Long id, Integer rating, Long idAnuncio, LocalDateTime ratedAt, LocalDateTime updatedAt) {
        this.id = id;
        this.rating = Long.valueOf(rating);
        this.idAnuncio = idAnuncio;
        this.ratedAt = ZonedDateTime.from(ratedAt);
        this.updatedAt = ZonedDateTime.from(updatedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRating() {
        return Math.toIntExact(rating);
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
