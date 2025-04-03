package com.hazmelaucb.ms_anuncios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hazmelaucb.ms_anuncios.exception.BadRequestException;
import com.hazmelaucb.ms_anuncios.exception.ResourceNotFoundException;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// @MockBean está deprecado desde Spring Boot 3.4.0, pero se mantiene por compatibilidad
// En futuras versiones considerar migrar a @Mock con MockitoExtension
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TagController.class)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    private ObjectMapper objectMapper;

    private TagDTO tagDTO;
    private List<TagDTO> tagDTOList;

    @BeforeEach
    void setup() {
        // Configurar objetos de prueba
        tagDTO = new TagDTO();
        tagDTO.setId(1);
        tagDTO.setNombre("Java");

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setId(2);
        tagDTO2.setNombre("Spring");

        tagDTOList = Arrays.asList(tagDTO, tagDTO2);
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaDeTags() throws Exception {
        // Arrange
        when(tagService.buscarTodos()).thenReturn(tagDTOList);

        // Act & Assert
        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].nombre", is("Java")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].nombre", is("Spring")));
    }

    @Test
    void obtenerPorId_CuandoExisteId_DeberiaRetornarTag() throws Exception {
        // Arrange
        when(tagService.buscarPorId(1L)).thenReturn(tagDTO);

        // Act & Assert
        mockMvc.perform(get("/api/tags/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Java")));
    }

    @Test
    void obtenerPorId_CuandoNoExisteId_DeberiaRetornarNotFound() throws Exception {
        // Arrange
        when(tagService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Tag no encontrado con id: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/tags/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Tag no encontrado con id: 99")));
    }

    @Test
    void obtenerPorNombre_CuandoExisteNombre_DeberiaRetornarTag() throws Exception {
        // Arrange
        when(tagService.buscarPorNombre("Java")).thenReturn(tagDTO);

        // Act & Assert
        mockMvc.perform(get("/api/tags/nombre/Java"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Java")));
    }

    @Test
    void buscarPorCoincidencia_DeberiaRetornarTagsCoincidentes() throws Exception {
        // Arrange
        when(tagService.buscarPorCoincidencia("Ja")).thenReturn(List.of(tagDTO));

        // Act & Assert
        mockMvc.perform(get("/api/tags/buscar").param("texto", "Ja"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Java")));
    }

    @Test
    void crear_ConDatosValidos_DeberiaCrearYRetornarTag() throws Exception {
        // Arrange
        TagDTO nuevoTag = new TagDTO();
        nuevoTag.setNombre("Python");

        when(tagService.crear(Mockito.any(TagDTO.class))).thenAnswer(invocation -> {
            TagDTO tag = invocation.getArgument(0);
            tag.setId(3);
            return tag;
        });

        // Act & Assert
        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoTag)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.nombre", is("Python")));
    }

    @Test
    void crear_ConNombreInvalido_DeberiaRetornarBadRequest() throws Exception {
        // Arrange
        TagDTO nuevoTag = new TagDTO();
        nuevoTag.setNombre(""); // Nombre vacío

        // Act & Assert
        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoTag)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void crear_ConNombreDuplicado_DeberiaRetornarBadRequest() throws Exception {
        // Arrange
        TagDTO nuevoTag = new TagDTO();
        nuevoTag.setNombre("Java");

        when(tagService.crear(Mockito.any(TagDTO.class))).thenThrow(
            new BadRequestException("Ya existe un tag con el nombre: Java"));

        // Act & Assert
        mockMvc.perform(post("/api/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevoTag)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Ya existe un tag con el nombre: Java")));
    }

    @Test
    void actualizar_ConDatosValidos_DeberiaActualizarYRetornarTag() throws Exception {
        // Arrange
        TagDTO tagActualizado = new TagDTO();
        tagActualizado.setNombre("Java Updated");

        when(tagService.actualizar(Mockito.any(Long.class), Mockito.any(TagDTO.class))).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            TagDTO tag = invocation.getArgument(1);
            tag.setId(id.intValue());
            return tag;
        });

        // Act & Assert
        mockMvc.perform(put("/api/tags/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Java Updated")));
    }

    @Test
    void eliminar_CuandoExisteId_DeberiaEliminarYRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(tagService).eliminar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tags/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void existePorNombre_CuandoExisteNombre_DeberiaRetornarTrue() throws Exception {
        // Arrange
        when(tagService.existePorNombre(anyString())).thenReturn(true);

        // Act & Assert
        mockMvc.perform(get("/api/tags/existe").param("nombre", "Java"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void existePorNombre_CuandoNoExisteNombre_DeberiaRetornarFalse() throws Exception {
        // Arrange
        when(tagService.existePorNombre(anyString())).thenReturn(false);

        // Act & Assert
        mockMvc.perform(get("/api/tags/existe").param("nombre", "Ruby"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
