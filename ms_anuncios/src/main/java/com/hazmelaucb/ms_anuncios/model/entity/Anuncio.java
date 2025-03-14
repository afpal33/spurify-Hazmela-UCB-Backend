package com.hazmelaucb.ms_anuncios.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "anuncio")
public class Anuncio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    
    @Column(name = "titulo", nullable = false)
    private String titulo;
    
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
    
    @Column(name = "area_especializacion", nullable = false)
    private String areaEspecializacion;
    
    @Column(name = "precio")
    private BigDecimal precio;
    
    @Column(name = "estado", nullable = false)
    private String estado;
    
    @Column(name = "created_at")
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;
    
    @ManyToMany
    @JoinTable(
        name = "anuncio_tag",
        joinColumns = @JoinColumn(name = "anuncio_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
    
    // Constructores
    public Anuncio() {
    }
    
    public Anuncio(Integer userId, String titulo, String descripcion, String areaEspecializacion, 
                  BigDecimal precio, String estado) {
        this.userId = userId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.areaEspecializacion = areaEspecializacion;
        this.precio = precio;
        this.estado = estado;
    }
    
    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAreaEspecializacion() {
        return areaEspecializacion;
    }

    public void setAreaEspecializacion(String areaEspecializacion) {
        this.areaEspecializacion = areaEspecializacion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
    
    public void agregarTag(Tag tag) {
        this.tags.add(tag);
        tag.getAnuncios().add(this);
    }
    
    public void removerTag(Tag tag) {
        this.tags.remove(tag);
        tag.getAnuncios().remove(this);
    }
}
