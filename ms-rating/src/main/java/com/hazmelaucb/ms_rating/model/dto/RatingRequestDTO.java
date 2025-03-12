package com.hazmelaucb.ms_rating.model.dto;

import com.hazmelaucb.ms_rating.model.entity.RatingEntity;

import java.time.ZonedDateTime;

public class RatingRequestDTO {
    private Integer id;
    private ZonedDateTime ratedAt;


    public RatingRequestDTO(RatingEntity rating){
        this.id = rating.getId_rating();
        this.ratedAt = rating.getRatedAt();
    }

    public RatingRequestDTO(Integer id, ZonedDateTime ratedAt) {
        this.id = id;
        this.ratedAt = ratedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ZonedDateTime getRatedAt() {
        return ratedAt;
    }

    public void setRatedAt(ZonedDateTime ratedAt) {
        this.ratedAt = ratedAt;
    }
}
