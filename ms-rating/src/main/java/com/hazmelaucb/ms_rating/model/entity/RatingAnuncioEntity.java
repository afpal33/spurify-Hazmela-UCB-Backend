package com.hazmelaucb.ms_rating.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rating_anuncio")
public class RatingAnuncioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ra")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "anuncio_id", nullable = false)
    private AnuncioEntity anuncio;

    @ManyToOne
    @JoinColumn(name = "rating_id", nullable = false)
    private RatingEntity rating;

    public RatingAnuncioEntity(Integer id, AnuncioEntity anuncio, RatingEntity rating) {
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

    public AnuncioEntity getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(AnuncioEntity anuncio) {
        this.anuncio = anuncio;
    }

    public RatingEntity getRating() {
        return rating;
    }

    public void setRating(RatingEntity rating) {
        this.rating = rating;
    }
}
