package com.hazmelaucb.ms_rating.model.dto;

import com.hazmelaucb.ms_rating.model.entity.RatingEntity;

import java.time.ZonedDateTime;

public class RatingResponseDTO {
    private Integer id;
    private Integer rating;
    private ZonedDateTime ratedAt;
    private ZonedDateTime updatedAt;

    public RatingResponseDTO(RatingEntity rating){
        this.id = rating.getId_rating();
        this.rating = rating.getRating();
        this.ratedAt = rating.getRatedAt();
        this.updatedAt = rating.getUpdatedAt();
    }

    public RatingResponseDTO(ZonedDateTime updatedAt, ZonedDateTime ratedAt, Integer rating, Integer id) {
        this.updatedAt = updatedAt;
        this.ratedAt = ratedAt;
        this.rating = rating;
        this.id = id;
    }
}
