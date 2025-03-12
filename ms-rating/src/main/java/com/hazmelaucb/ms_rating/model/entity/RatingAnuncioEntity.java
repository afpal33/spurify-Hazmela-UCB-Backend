package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rating_anuncio")
public class RatingAnuncioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ra")
    private Integer id;

    @Column(name = "anuncio_id", nullable = false)
    private Integer anuncio;

    @Column(name = "rating_id", nullable = false)
    private Integer rating;

    public RatingAnuncioEntity(Integer id, Integer anuncio, Integer rating) {
        this.id = id;
        this.anuncio = anuncio;
        this.rating = rating;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Integer anuncio) {
        this.anuncio = anuncio;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
