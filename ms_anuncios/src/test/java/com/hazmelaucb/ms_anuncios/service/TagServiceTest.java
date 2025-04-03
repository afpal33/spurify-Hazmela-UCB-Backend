package com.hazmelaucb.ms_anuncios.service;

import com.hazmelaucb.ms_anuncios.exception.BadRequestException;
import com.hazmelaucb.ms_anuncios.exception.ResourceNotFoundException;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.model.entity.Tag;
import com.hazmelaucb.ms_anuncios.repository.TagRepository;
import com.hazmelaucb.ms_anuncios.service.impl.TagServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    private Tag tag1;
    private Tag tag2;
    private TagDTO tagDTO1;
    private TagDTO tagDTO2;

    @BeforeEach
    void setup() {
        // Configurar entidades de prueba
        tag1 = new Tag();
        tag1.setId(1);
        tag1.setNombre("Java");

        tag2 = new Tag();
        tag2.setId(2);
        tag2.setNombre("Spring");

        // Configurar DTOs de prueba
        tagDTO1 = new TagDTO();
        tagDTO1.setId(1);
        tagDTO1.setNombre("Java");

        tagDTO2 = new TagDTO();
        tagDTO2.setId(2);
        tagDTO2.setNombre("Spring");
    }

    @Test
    void buscarTodos_DeberiaRetornarListaDeTags() {
        // Arrange
        when(tagRepository.findAll()).thenReturn(Arrays.asList(tag1, tag2));

        // Act
        List<TagDTO> resultado = tagService.buscarTodos();

        // Assert
        assertEquals(2, resultado.size());
        assertEquals("Java", resultado.get(0).getNombre());
        assertEquals("Spring", resultado.get(1).getNombre());
        verify(tagRepository).findAll();
    }

    @Test
    void buscarPorId_CuandoExisteId_DeberiaRetornarTag() {
        // Arrange
        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag1));

        // Act
        TagDTO resultado = tagService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Java", resultado.getNombre());
        verify(tagRepository).findById(1L);
    }

    @Test
    void buscarPorId_CuandoNoExisteId_DeberiaLanzarException() {
        // Arrange
        when(tagRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.buscarPorId(99L);
        });
        verify(tagRepository).findById(99L);
    }

    @Test
    void buscarPorNombre_CuandoExisteNombre_DeberiaRetornarTag() {
        // Arrange
        when(tagRepository.findByNombre("Java")).thenReturn(Optional.of(tag1));

        // Act
        TagDTO resultado = tagService.buscarPorNombre("Java");

        // Assert
        assertNotNull(resultado);
        assertEquals("Java", resultado.getNombre());
        verify(tagRepository).findByNombre("Java");
    }

    @Test
    void buscarPorNombre_CuandoNoExisteNombre_DeberiaLanzarException() {
        // Arrange
        when(tagRepository.findByNombre("NoExiste")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.buscarPorNombre("NoExiste");
        });
        verify(tagRepository).findByNombre("NoExiste");
    }

    @Test
    void buscarPorCoincidencia_DeberiaRetornarTagsCoincidentes() {
        // Arrange
        when(tagRepository.findByNombreContainingIgnoreCase("Ja")).thenReturn(List.of(tag1));

        // Act
        List<TagDTO> resultado = tagService.buscarPorCoincidencia("Ja");

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Java", resultado.get(0).getNombre());
        verify(tagRepository).findByNombreContainingIgnoreCase("Ja");
    }

    @Test
    void crear_ConNombreUnico_DeberiaCrearTag() {
        // Arrange
        TagDTO nuevoTagDTO = new TagDTO();
        nuevoTagDTO.setNombre("Python");

        Tag nuevoTag = new Tag();
        nuevoTag.setNombre("Python");

        Tag tagGuardado = new Tag();
        tagGuardado.setId(3);
        tagGuardado.setNombre("Python");

        when(tagRepository.existsByNombre("Python")).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(tagGuardado);

        // Act
        TagDTO resultado = tagService.crear(nuevoTagDTO);

        // Assert
        assertNotNull(resultado);
        assertEquals(3, resultado.getId());
        assertEquals("Python", resultado.getNombre());
        verify(tagRepository).existsByNombre("Python");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void crear_ConNombreDuplicado_DeberiaLanzarException() {
        // Arrange
        TagDTO nuevoTagDTO = new TagDTO();
        nuevoTagDTO.setNombre("Java");

        when(tagRepository.existsByNombre("Java")).thenReturn(true);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> {
            tagService.crear(nuevoTagDTO);
        });
        verify(tagRepository).existsByNombre("Java");
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    void actualizar_CuandoExisteId_DeberiaActualizarTag() {
        // Arrange
        TagDTO actualizarTagDTO = new TagDTO();
        actualizarTagDTO.setNombre("Java Updated");

        Tag tagExistente = new Tag();
        tagExistente.setId(1);
        tagExistente.setNombre("Java");

        Tag tagActualizado = new Tag();
        tagActualizado.setId(1);
        tagActualizado.setNombre("Java Updated");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tagExistente));
        when(tagRepository.existsByNombre("Java Updated")).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(tagActualizado);

        // Act
        TagDTO resultado = tagService.actualizar(1L, actualizarTagDTO);

        // Assert
        assertEquals(1, resultado.getId());
        assertEquals("Java Updated", resultado.getNombre());
        verify(tagRepository).findById(1L);
        verify(tagRepository).existsByNombre("Java Updated");
        verify(tagRepository).save(any(Tag.class));
    }

    @Test
    void actualizar_CuandoNoExisteId_DeberiaLanzarException() {
        // Arrange
        TagDTO actualizarTagDTO = new TagDTO();
        actualizarTagDTO.setNombre("Java Updated");

        when(tagRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.actualizar(99L, actualizarTagDTO);
        });
        verify(tagRepository).findById(99L);
        verify(tagRepository, never()).save(any(Tag.class));
    }

    @Test
    void eliminar_CuandoExisteId_DeberiaEliminarTag() {
        // Arrange
        when(tagRepository.existsById(1L)).thenReturn(true);
        doNothing().when(tagRepository).deleteById(1L);

        // Act
        tagService.eliminar(1L);

        // Assert
        verify(tagRepository).existsById(1L);
        verify(tagRepository).deleteById(1L);
    }

    @Test
    void eliminar_CuandoNoExisteId_DeberiaLanzarException() {
        // Arrange
        when(tagRepository.existsById(99L)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            tagService.eliminar(99L);
        });
        verify(tagRepository).existsById(99L);
        verify(tagRepository, never()).deleteById(anyLong());
    }

    @Test
    void existePorNombre_CuandoExisteNombre_DeberiaRetornarTrue() {
        // Arrange
        when(tagRepository.existsByNombre("Java")).thenReturn(true);

        // Act
        boolean resultado = tagService.existePorNombre("Java");

        // Assert
        assertTrue(resultado);
        verify(tagRepository).existsByNombre("Java");
    }

    @Test
    void existePorNombre_CuandoNoExisteNombre_DeberiaRetornarFalse() {
        // Arrange
        when(tagRepository.existsByNombre("Ruby")).thenReturn(false);

        // Act
        boolean resultado = tagService.existePorNombre("Ruby");

        // Assert
        assertFalse(resultado);
        verify(tagRepository).existsByNombre("Ruby");
    }
}
