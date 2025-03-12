package com.hazmelaucb.ms_anuncios.service;

import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;

import java.util.List;

public interface TagService {
    
    List<TagDTO> buscarTodos();
    
    TagDTO buscarPorId(Long id);
    
    TagDTO buscarPorNombre(String nombre);
    
    List<TagDTO> buscarPorCoincidencia(String nombreParcial);
    
    TagDTO crear(TagDTO tagDTO);
    
    TagDTO actualizar(Long id, TagDTO tagDTO);
    
    void eliminar(Long id);
    
    boolean existePorNombre(String nombre);
}
