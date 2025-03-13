package com.hazmelaucb.ms_rating.model.dto;

import com.hazmelaucb.ms_rating.model.entity.RatingEntity;

import java.time.ZonedDateTime;

public class RatingResponseDTO {
    private Long id;
    private Long rating;
    private ZonedDateTime ratedAt;
    private ZonedDateTime updatedAt;

    public RatingResponseDTO(RatingEntity rating){
        this.id = rating.getId_rating();
        this.rating = rating.getRating();
        this.ratedAt = rating.getRatedAt();
        this.updatedAt = rating.getUpdatedAt();
    }

    public RatingResponseDTO(ZonedDateTime updatedAt, ZonedDateTime ratedAt, Long rating, Long id) {
        this.updatedAt = updatedAt;
        this.ratedAt = ratedAt;
        this.rating = rating;
        this.id = id;
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
