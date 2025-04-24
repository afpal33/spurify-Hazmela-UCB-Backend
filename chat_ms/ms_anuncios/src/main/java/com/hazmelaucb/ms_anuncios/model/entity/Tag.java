package com.hazmelaucb.ms_anuncios.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "tag")
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;
    
    @ManyToMany(mappedBy = "tags")
    private Set<Anuncio> anuncios = new HashSet<>();
    
    // Constructores
    public Tag() {
    }
    
    public Tag(String nombre) {
        this.nombre = nombre;
    }
    
    public Tag(Integer id, String nombre, Set<Anuncio> anuncios) {
        this.id = id;
        this.nombre = nombre;
        this.anuncios = anuncios;
    }
    
    // Getters y setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Set<Anuncio> getAnuncios() {
        return anuncios;
    }
    
    public void setAnuncios(Set<Anuncio> anuncios) {
        this.anuncios = anuncios;
    }
    
    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(id, tag.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    // toString
    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
