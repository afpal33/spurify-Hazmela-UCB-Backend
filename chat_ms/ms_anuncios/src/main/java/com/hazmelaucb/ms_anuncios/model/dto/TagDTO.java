package com.hazmelaucb.ms_anuncios.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Objects;

public class TagDTO {
    
    private Integer id;
    
    @NotBlank(message = "El nombre del tag no puede estar vacío")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;
    
    // Constructores
    public TagDTO() {
    }
    
    public TagDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    
    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDTO tagDTO = (TagDTO) o;
        return Objects.equals(id, tagDTO.id) && 
               Objects.equals(nombre, tagDTO.nombre);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }
    
    // toString
    @Override
    public String toString() {
        return "TagDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
