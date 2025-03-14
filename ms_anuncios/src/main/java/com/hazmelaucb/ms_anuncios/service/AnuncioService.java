package com.hazmelaucb.ms_anuncios.service;

import com.hazmelaucb.ms_anuncios.model.dto.AnuncioCrearDTO;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioDTO;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.model.entity.Anuncio;
import com.hazmelaucb.ms_anuncios.model.enums.EstadoAnuncio;
import com.hazmelaucb.ms_anuncios.repository.AnuncioRepository;
import com.hazmelaucb.ms_anuncios.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AnuncioService {
    
    private final AnuncioRepository anuncioRepository;
    private final TagRepository tagRepository;
    
    @Autowired
    public AnuncioService(AnuncioRepository anuncioRepository, TagRepository tagRepository) {
        this.anuncioRepository = anuncioRepository;
        this.tagRepository = tagRepository;
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarTodos() {
        return anuncioRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public AnuncioDTO buscarPorId(Long id) {
        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado con ID: " + id));
        return convertirADTO(anuncio);
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorUsuario(Integer userId) {
        return anuncioRepository.findByUserId(userId).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorAreaEspecializacion(String areaEspecializacion) {
        return anuncioRepository.findByAreaEspecializacion(areaEspecializacion).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorEstado(String estado) {
        return anuncioRepository.findByEstado(estado).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        return anuncioRepository.findByPrecioBetween(precioMin, precioMax).stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AnuncioDTO crear(AnuncioCrearDTO anuncioCrearDTO) {
        Anuncio anuncio = new Anuncio();
        anuncio.setUserId(anuncioCrearDTO.getUserId());
        anuncio.setTitulo(anuncioCrearDTO.getTitulo());
        anuncio.setDescripcion(anuncioCrearDTO.getDescripcion());
        anuncio.setAreaEspecializacion(anuncioCrearDTO.getAreaEspecializacion());
        anuncio.setPrecio(anuncioCrearDTO.getPrecio());
        
        // Si no se especifica un estado, se establece como DRAFT por defecto
        String estado = anuncioCrearDTO.getEstado();
        if (estado == null || estado.isEmpty()) {
            estado = EstadoAnuncio.DRAFT.toString();
        }
        anuncio.setEstado(estado);
        
        // Las fechas las establece la DB por defecto, pero las configuramos por si acaso
        ZonedDateTime ahora = ZonedDateTime.now();
        anuncio.setCreatedAt(ahora);
        anuncio.setUpdatedAt(ahora);
        
        return convertirADTO(anuncioRepository.save(anuncio));
    }
    
    @Transactional
    public AnuncioDTO actualizar(Long id, AnuncioCrearDTO anuncioCrearDTO) {
        Anuncio anuncioExistente = anuncioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado con ID: " + id));
        
        anuncioExistente.setTitulo(anuncioCrearDTO.getTitulo());
        anuncioExistente.setDescripcion(anuncioCrearDTO.getDescripcion());
        anuncioExistente.setAreaEspecializacion(anuncioCrearDTO.getAreaEspecializacion());
        anuncioExistente.setPrecio(anuncioCrearDTO.getPrecio());
        
        if (anuncioCrearDTO.getEstado() != null && !anuncioCrearDTO.getEstado().isEmpty()) {
            anuncioExistente.setEstado(anuncioCrearDTO.getEstado());
        }
        
        // La fecha updated_at se actualizará automáticamente por el trigger de la base de datos
        
        return convertirADTO(anuncioRepository.save(anuncioExistente));
    }
    
    @Transactional
    public void eliminar(Long id) {
        if (!anuncioRepository.existsById(id)) {
            throw new NoSuchElementException("Anuncio no encontrado con ID: " + id);
        }
        anuncioRepository.deleteById(id);
    }
    
    @Transactional
    public AnuncioDTO cambiarEstado(Long id, String nuevoEstado) {
        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado con ID: " + id));
        
        try {
            EstadoAnuncio.valueOf(nuevoEstado);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado no válido: " + nuevoEstado);
        }
        
        anuncio.setEstado(nuevoEstado);
        return convertirADTO(anuncioRepository.save(anuncio));
    }
    
    @Transactional
    public AnuncioDTO agregarTagAAnuncio(Integer anuncioId, Integer tagId) {
        Anuncio anuncio = anuncioRepository.findById(anuncioId.longValue())
                .orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado con ID: " + anuncioId));
            
        com.hazmelaucb.ms_anuncios.model.entity.Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(() -> new NoSuchElementException("Tag no encontrado con ID: " + tagId));
        
        anuncio.agregarTag(tag);
        anuncio = anuncioRepository.save(anuncio);
        
        return convertirADTO(anuncio);
    }
    
    @Transactional(readOnly = true)
    public List<TagDTO> obtenerTagsDeAnuncio(Integer anuncioId) {
        Anuncio anuncio = anuncioRepository.findById(anuncioId.longValue())
                .orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado con ID: " + anuncioId));
            
        return anuncio.getTags().stream()
                .map(this::convertirTagADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AnuncioDTO eliminarTagDeAnuncio(Integer anuncioId, Integer tagId) {
        Anuncio anuncio = anuncioRepository.findById(anuncioId.longValue())
                .orElseThrow(() -> new NoSuchElementException("Anuncio no encontrado con ID: " + anuncioId));
            
        com.hazmelaucb.ms_anuncios.model.entity.Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(() -> new NoSuchElementException("Tag no encontrado con ID: " + tagId));
        
        if (!anuncio.getTags().contains(tag)) {
            throw new IllegalArgumentException("La etiqueta con ID: " + tagId + " no está asociada al anuncio con ID: " + anuncioId);
        }
        
        anuncio.removerTag(tag);
        anuncio = anuncioRepository.save(anuncio);
        
        return convertirADTO(anuncio);
    }
    
    // Método auxiliar para convertir Tag a TagDTO
    private TagDTO convertirTagADTO(com.hazmelaucb.ms_anuncios.model.entity.Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(tag.getId());
        dto.setNombre(tag.getNombre());
        return dto;
    }
    
    // Método actualizado para convertir Anuncio a AnuncioDTO
    private AnuncioDTO convertirADTO(Anuncio anuncio) {
        AnuncioDTO dto = new AnuncioDTO();
        dto.setId(anuncio.getId());
        dto.setUserId(anuncio.getUserId());
        dto.setTitulo(anuncio.getTitulo());
        dto.setDescripcion(anuncio.getDescripcion());
        dto.setAreaEspecializacion(anuncio.getAreaEspecializacion());
        dto.setPrecio(anuncio.getPrecio());
        dto.setEstado(anuncio.getEstado());
        dto.setCreatedAt(anuncio.getCreatedAt());
        dto.setUpdatedAt(anuncio.getUpdatedAt());
        
        // Añadir tags al DTO
        if (anuncio.getTags() != null) {
            anuncio.getTags().forEach(tag -> {
                dto.getTags().add(convertirTagADTO(tag));
            });
        }
        
        return dto;
    }
}
