package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.ZonedDateTime;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_rating;

    @Column(nullable = false)
    private Integer rating;

    @Column(name = "rated_at", nullable = false)
    private ZonedDateTime ratedAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    public RatingEntity(Integer id_rating, Integer rating, ZonedDateTime updatedAt, ZonedDateTime ratedAt) {
        this.id_rating = id_rating;
        this.rating = rating;
        this.updatedAt = updatedAt;
        this.ratedAt = ratedAt;
    }

    public Integer getId_rating() {
        return id_rating;
    }

    public void setId_rating(Integer id_rating) {
        this.id_rating = id_rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
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
