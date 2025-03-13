package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

import java.io.Serial;
import java.time.ZonedDateTime;

@Entity
@Table(name = "rating")
public class RatingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rating;

    @Column(nullable = false)
    private Long rating;

    @Column(name = "rated_at", nullable = false)
    private ZonedDateTime ratedAt;

    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    public RatingEntity(Long id_rating, Long rating, ZonedDateTime updatedAt, ZonedDateTime ratedAt) {
        this.id_rating = id_rating;
        this.rating = rating;
        this.updatedAt = updatedAt;
        this.ratedAt = ratedAt;
    }

    public RatingEntity() {

    }

    public Long getId_rating() {
        return id_rating;
    }

    public void setId_rating(Long id_rating) {
        this.id_rating = id_rating;
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
