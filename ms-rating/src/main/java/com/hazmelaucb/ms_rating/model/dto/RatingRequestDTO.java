package com.hazmelaucb.ms_rating.model.dto;

import com.hazmelaucb.ms_rating.model.entity.RatingEntity;

import java.time.ZonedDateTime;

public class RatingRequestDTO {
    private Long id;
    private ZonedDateTime ratedAt;


    public RatingRequestDTO(RatingEntity rating){
        this.id = rating.getId_rating();
        this.ratedAt = rating.getRatedAt();
    }

    public RatingRequestDTO(Long id, ZonedDateTime ratedAt) {
        this.id = id;
        this.ratedAt = ratedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(ZonedDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }
}
