package com.hazmelaucb.ms_anuncios.service.impl;

import com.hazmelaucb.ms_anuncios.exception.BadRequestException;
import com.hazmelaucb.ms_anuncios.exception.ResourceNotFoundException;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.model.entity.Tag;
import com.hazmelaucb.ms_anuncios.repository.TagRepository;
import com.hazmelaucb.ms_anuncios.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    
    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }
    
    @Override
    public List<TagDTO> buscarTodos() {
        return tagRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public TagDTO buscarPorId(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con id: " + id));
        return convertirADTO(tag);
    }
    
    @Override
    public TagDTO buscarPorNombre(String nombre) {
        Tag tag = tagRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con nombre: " + nombre));
        return convertirADTO(tag);
    }
    
    @Override
    public List<TagDTO> buscarPorCoincidencia(String nombreParcial) {
        List<Tag> tags = tagRepository.findByNombreContainingIgnoreCase(nombreParcial);
        return tags.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    public TagDTO crear(TagDTO tagDTO) {
        if (tagRepository.existsByNombre(tagDTO.getNombre())) {
            throw new BadRequestException("Ya existe un tag con el nombre: " + tagDTO.getNombre());
        }
        
        try {
            Tag tag = convertirAEntidad(tagDTO);
            Tag tagGuardado = tagRepository.save(tag);
            return convertirADTO(tagGuardado);
        } catch (Exception e) {
            throw new BadRequestException("Error al crear el tag: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional
    public TagDTO actualizar(Long id, TagDTO tagDTO) {
        // Verificar si existe el tag
        Tag tagExistente = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con id: " + id));
                
        // Verificar si existe otro tag con el mismo nombre
        if (!tagExistente.getNombre().equals(tagDTO.getNombre()) && 
            tagRepository.existsByNombre(tagDTO.getNombre())) {
            throw new BadRequestException("Ya existe otro tag con el nombre: " + tagDTO.getNombre());
        }
        
        try {
            tagExistente.setNombre(tagDTO.getNombre());
            Tag tagActualizado = tagRepository.save(tagExistente);
            return convertirADTO(tagActualizado);
        } catch (Exception e) {
            throw new BadRequestException("Error al actualizar el tag: " + e.getMessage(), e);
        }
    }
    
    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tag no encontrado con id: " + id);
        }
        
        try {
            tagRepository.deleteById(id);
        } catch (Exception e) {
            throw new BadRequestException("Error al eliminar el tag: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean existePorNombre(String nombre) {
        return tagRepository.existsByNombre(nombre);
    }
    
    private TagDTO convertirADTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setNombre(tag.getNombre());
        return dto;
    }
    
    private Tag convertirAEntidad(TagDTO tagDTO) {
        Tag tag = new Tag();
        tag.setNombre(tagDTO.getNombre());
        return tag;
    }
}
