package com.hazmelaucb.ms_anuncios.service;

import com.hazmelaucb.ms_anuncios.exception.AnuncioValidationException;
import com.hazmelaucb.ms_anuncios.exception.ResourceNotFoundException;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioCrearDTO;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioDTO;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.model.entity.Anuncio;
import com.hazmelaucb.ms_anuncios.model.enums.EstadoAnuncio;
import com.hazmelaucb.ms_anuncios.repository.AnuncioRepository;
import com.hazmelaucb.ms_anuncios.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnuncioService {
    
    private final AnuncioRepository anuncioRepository;    private final TagRepository tagRepository;
    
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
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con ID: " + id));
        return convertirADTO(anuncio);
    }
      @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorUsuario(String userId) {
        List<Anuncio> anuncios = anuncioRepository.findByUserId(userId);
        if (anuncios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron anuncios para el usuario con ID: " + userId);
        }
        return anuncios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorAreaEspecializacion(String areaEspecializacion) {
        List<Anuncio> anuncios = anuncioRepository.findByAreaEspecializacion(areaEspecializacion);
        if (anuncios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron anuncios para el área: " + areaEspecializacion);
        }
        return anuncios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorEstado(String estado) {
        // Validar que el estado proporcionado sea válido
        validarEstadoAnuncio(estado);
        
        List<Anuncio> anuncios = anuncioRepository.findByEstado(estado);
        if (anuncios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron anuncios con el estado: " + estado);
        }
        return anuncios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AnuncioDTO> buscarPorRangoPrecio(BigDecimal precioMin, BigDecimal precioMax) {
        // Validar que el precio mínimo no sea mayor que el precio máximo
        if (precioMin.compareTo(precioMax) > 0) {
            throw new AnuncioValidationException("El precio mínimo no puede ser mayor que el precio máximo");
        }
        
        List<Anuncio> anuncios = anuncioRepository.findByPrecioBetween(precioMin, precioMax);
        if (anuncios.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron anuncios en el rango de precio: " + precioMin + " - " + precioMax);
        }
        return anuncios.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AnuncioDTO crear(AnuncioCrearDTO anuncioCrearDTO) {
        // Validar datos antes de crear
        validarAnuncioCrear(anuncioCrearDTO);
        
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
        } else {
            validarEstadoAnuncio(estado);
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
        // Validar datos antes de actualizar
        validarAnuncioCrear(anuncioCrearDTO);
        
        Anuncio anuncioExistente = anuncioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con ID: " + id));
        
        anuncioExistente.setTitulo(anuncioCrearDTO.getTitulo());
        anuncioExistente.setDescripcion(anuncioCrearDTO.getDescripcion());
        anuncioExistente.setAreaEspecializacion(anuncioCrearDTO.getAreaEspecializacion());
        anuncioExistente.setPrecio(anuncioCrearDTO.getPrecio());
        
        if (anuncioCrearDTO.getEstado() != null && !anuncioCrearDTO.getEstado().isEmpty()) {
            validarEstadoAnuncio(anuncioCrearDTO.getEstado());
            anuncioExistente.setEstado(anuncioCrearDTO.getEstado());
        }
        
        // La fecha updated_at se actualizará automáticamente por el trigger de la base de datos
        
        return convertirADTO(anuncioRepository.save(anuncioExistente));
    }
    
    @Transactional
    public void eliminar(Long id) {
        if (!anuncioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Anuncio no encontrado con ID: " + id);
        }
        anuncioRepository.deleteById(id);
    }
    
    @Transactional
    public AnuncioDTO cambiarEstado(Long id, String nuevoEstado) {
        validarEstadoAnuncio(nuevoEstado);
        
        Anuncio anuncio = anuncioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con ID: " + id));
        
        anuncio.setEstado(nuevoEstado);
        return convertirADTO(anuncioRepository.save(anuncio));
    }
    
    @Transactional
    public AnuncioDTO agregarTagAAnuncio(Integer anuncioId, Integer tagId) {
        Anuncio anuncio = anuncioRepository.findById(anuncioId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con ID: " + anuncioId));
            
        com.hazmelaucb.ms_anuncios.model.entity.Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con ID: " + tagId));
        
        // Validar que el tag no esté ya asociado al anuncio
        if (anuncio.getTags().contains(tag)) {
            throw new AnuncioValidationException("El tag con ID " + tagId + " ya está asociado al anuncio con ID " + anuncioId);
        }
        
        anuncio.agregarTag(tag);
        anuncio = anuncioRepository.save(anuncio);
        
        return convertirADTO(anuncio);
    }
    
    @Transactional(readOnly = true)
    public List<TagDTO> obtenerTagsDeAnuncio(Integer anuncioId) {
        Anuncio anuncio = anuncioRepository.findById(anuncioId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con ID: " + anuncioId));
            
        return anuncio.getTags().stream()
                .map(this::convertirTagADTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public AnuncioDTO eliminarTagDeAnuncio(Integer anuncioId, Integer tagId) {
        Anuncio anuncio = anuncioRepository.findById(anuncioId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Anuncio no encontrado con ID: " + anuncioId));
            
        com.hazmelaucb.ms_anuncios.model.entity.Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(() -> new ResourceNotFoundException("Tag no encontrado con ID: " + tagId));
        
        if (!anuncio.getTags().contains(tag)) {
            throw new AnuncioValidationException("La etiqueta con ID: " + tagId + " no está asociada al anuncio con ID: " + anuncioId);
        }
        
        anuncio.removerTag(tag);
        anuncio = anuncioRepository.save(anuncio);
        
        return convertirADTO(anuncio);
    }
    
    // Método para validar el estado del anuncio
    private void validarEstadoAnuncio(String estado) {
        try {
            EstadoAnuncio.valueOf(estado);
        } catch (IllegalArgumentException e) {
            List<String> estadosValidos = Arrays.stream(EstadoAnuncio.values())
                    .map(EstadoAnuncio::name)
                    .collect(Collectors.toList());
            
            throw new AnuncioValidationException("Estado no válido: " + estado + ". Los estados permitidos son: " + estadosValidos);
        }
    }
    
    // Método para validar los datos de un anuncio al crear o actualizar
    private void validarAnuncioCrear(AnuncioCrearDTO anuncioDTO) {
        if (anuncioDTO.getPrecio() != null && anuncioDTO.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new AnuncioValidationException("El precio no puede ser negativo");
        }
        
        if (anuncioDTO.getEstado() != null && !anuncioDTO.getEstado().isEmpty()) {
            validarEstadoAnuncio(anuncioDTO.getEstado());
        }
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
