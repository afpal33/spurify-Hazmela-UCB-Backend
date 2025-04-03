package com.hazmelaucb.ms_anuncios.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hazmelaucb.ms_anuncios.exception.AnuncioValidationException;
import com.hazmelaucb.ms_anuncios.exception.ResourceNotFoundException;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioCrearDTO;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioDTO;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.model.enums.EstadoAnuncio;
import com.hazmelaucb.ms_anuncios.service.AnuncioService;
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

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AnuncioController.class)
public class AnuncioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnuncioService anuncioService;

    private ObjectMapper objectMapper;
    private AnuncioDTO anuncioDTO;
    private AnuncioCrearDTO anuncioCrearDTO;
    private List<AnuncioDTO> anuncioDTOList;
    private List<TagDTO> tagDTOList;

    @BeforeEach
    void setup() {
        // Configurar ObjectMapper para manejar fechas
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        // Configurar objetos de prueba
        anuncioDTO = new AnuncioDTO();
        anuncioDTO.setId(1);
        anuncioDTO.setUserId(101);
        anuncioDTO.setTitulo("Clases de Programación");
        anuncioDTO.setDescripcion("Clases particulares de programación Java");
        anuncioDTO.setAreaEspecializacion("Tecnología");
        anuncioDTO.setPrecio(new BigDecimal("150.00"));
        anuncioDTO.setEstado(EstadoAnuncio.PUBLISHED.toString());
        anuncioDTO.setCreatedAt(ZonedDateTime.now());
        anuncioDTO.setUpdatedAt(ZonedDateTime.now());

        // Crear segundo anuncio para lista
        AnuncioDTO anuncioDTO2 = new AnuncioDTO();
        anuncioDTO2.setId(2);
        anuncioDTO2.setUserId(102);
        anuncioDTO2.setTitulo("Tutoría de Matemáticas");
        anuncioDTO2.setDescripcion("Tutoría de matemáticas para nivel universitario");
        anuncioDTO2.setAreaEspecializacion("Educación");
        anuncioDTO2.setPrecio(new BigDecimal("100.00"));
        anuncioDTO2.setEstado(EstadoAnuncio.PUBLISHED.toString());
        anuncioDTO2.setCreatedAt(ZonedDateTime.now());
        anuncioDTO2.setUpdatedAt(ZonedDateTime.now());

        anuncioDTOList = Arrays.asList(anuncioDTO, anuncioDTO2);

        // Crear DTOs para crear anuncios
        anuncioCrearDTO = new AnuncioCrearDTO();
        anuncioCrearDTO.setUserId(101);
        anuncioCrearDTO.setTitulo("Clases de Programación");
        anuncioCrearDTO.setDescripcion("Clases particulares de programación Java");
        anuncioCrearDTO.setAreaEspecializacion("Tecnología");
        anuncioCrearDTO.setPrecio(new BigDecimal("150.00"));
        anuncioCrearDTO.setEstado(EstadoAnuncio.DRAFT.toString());

        // Tags para pruebas
        TagDTO tagDTO1 = new TagDTO();
        tagDTO1.setId(1);
        tagDTO1.setNombre("Java");

        TagDTO tagDTO2 = new TagDTO();
        tagDTO2.setId(2);
        tagDTO2.setNombre("Programación");

        tagDTOList = Arrays.asList(tagDTO1, tagDTO2);
    }

    @Test
    void obtenerTodos_DeberiaRetornarListaDeAnuncios() throws Exception {
        // Arrange
        when(anuncioService.buscarTodos()).thenReturn(anuncioDTOList);

        // Act & Assert
        mockMvc.perform(get("/api/anuncios"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].titulo", is("Clases de Programación")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].titulo", is("Tutoría de Matemáticas")));
    }

    @Test
    void obtenerPorId_CuandoExisteId_DeberiaRetornarAnuncio() throws Exception {
        // Arrange
        when(anuncioService.buscarPorId(1L)).thenReturn(anuncioDTO);

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("Clases de Programación")))
                .andExpect(jsonPath("$.areaEspecializacion", is("Tecnología")));
    }

    @Test
    void obtenerPorId_CuandoNoExisteId_DeberiaRetornarNotFound() throws Exception {
        // Arrange
        when(anuncioService.buscarPorId(99L)).thenThrow(new ResourceNotFoundException("Anuncio no encontrado con ID: 99"));

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Anuncio no encontrado")));
    }

    @Test
    void obtenerPorUsuario_CuandoExistenAnuncios_DeberiaRetornarLista() throws Exception {
        // Arrange
        when(anuncioService.buscarPorUsuario(101)).thenReturn(List.of(anuncioDTO));

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/usuario/101"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId", is(101)));
    }

    @Test
    void obtenerPorArea_CuandoExistenAnuncios_DeberiaRetornarLista() throws Exception {
        // Arrange
        when(anuncioService.buscarPorAreaEspecializacion("Tecnología")).thenReturn(List.of(anuncioDTO));

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/area/Tecnología"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].areaEspecializacion", is("Tecnología")));
    }

    @Test
    void obtenerPorEstado_CuandoExistenAnuncios_DeberiaRetornarLista() throws Exception {
        // Arrange
        when(anuncioService.buscarPorEstado("PUBLISHED")).thenReturn(anuncioDTOList);

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/estado/PUBLISHED"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].estado", is("PUBLISHED")))
                .andExpect(jsonPath("$[1].estado", is("PUBLISHED")));
    }

    @Test
    void obtenerPorRangoPrecio_CuandoExistenAnuncios_DeberiaRetornarLista() throws Exception {
        // Arrange
        when(anuncioService.buscarPorRangoPrecio(Mockito.any(BigDecimal.class), Mockito.any(BigDecimal.class)))
                .thenReturn(anuncioDTOList);

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/precio")
                .param("min", "50.0")
                .param("max", "200.0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void crear_ConDatosValidos_DeberiaCrearYRetornarAnuncio() throws Exception {
        // Arrange
        when(anuncioService.crear(Mockito.any(AnuncioCrearDTO.class))).thenReturn(anuncioDTO);

        // Act & Assert
        mockMvc.perform(post("/api/anuncios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(anuncioCrearDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("Clases de Programación")));
    }

    @Test
    void crear_ConDatosInvalidos_DeberiaRetornarBadRequest() throws Exception {
        // Arrange
        AnuncioCrearDTO anuncioInvalido = new AnuncioCrearDTO();
        anuncioInvalido.setUserId(101);
        anuncioInvalido.setTitulo(""); // Título vacío
        anuncioInvalido.setDescripcion("Descripción corta");
        anuncioInvalido.setAreaEspecializacion("Tecnología");
        anuncioInvalido.setPrecio(new BigDecimal("150.00"));

        // Act & Assert
        mockMvc.perform(post("/api/anuncios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(anuncioInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizar_ConDatosValidos_DeberiaActualizarYRetornarAnuncio() throws Exception {
        // Arrange
        AnuncioCrearDTO anuncioActualizar = new AnuncioCrearDTO();
        anuncioActualizar.setUserId(101);
        anuncioActualizar.setTitulo("Clases de Programación Avanzada");
        anuncioActualizar.setDescripcion("Clases particulares de programación Java y Spring Boot");
        anuncioActualizar.setAreaEspecializacion("Tecnología");
        anuncioActualizar.setPrecio(new BigDecimal("200.00"));
        
        AnuncioDTO anuncioActualizado = new AnuncioDTO();
        anuncioActualizado.setId(1);
        anuncioActualizado.setUserId(101);
        anuncioActualizado.setTitulo("Clases de Programación Avanzada");
        anuncioActualizado.setDescripcion("Clases particulares de programación Java y Spring Boot");
        anuncioActualizado.setAreaEspecializacion("Tecnología");
        anuncioActualizado.setPrecio(new BigDecimal("200.00"));
        anuncioActualizado.setEstado("PUBLISHED");
        
        when(anuncioService.actualizar(eq(1L), Mockito.any(AnuncioCrearDTO.class))).thenReturn(anuncioActualizado);

        // Act & Assert
        mockMvc.perform(put("/api/anuncios/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(anuncioActualizar)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("Clases de Programación Avanzada")))
                .andExpect(jsonPath("$.precio", is(200.00)));
    }

    @Test
    void eliminar_CuandoExisteId_DeberiaEliminarYRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(anuncioService).eliminar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/anuncios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void cambiarEstado_ConEstadoValido_DeberiaActualizarYRetornarAnuncio() throws Exception {
        // Arrange
        AnuncioDTO anuncioActualizado = new AnuncioDTO();
        anuncioActualizado.setId(1);
        anuncioActualizado.setEstado("ARCHIVED");
        anuncioActualizado.setTitulo(anuncioDTO.getTitulo());
        anuncioActualizado.setDescripcion(anuncioDTO.getDescripcion());
        anuncioActualizado.setUserId(anuncioDTO.getUserId());
        anuncioActualizado.setAreaEspecializacion(anuncioDTO.getAreaEspecializacion());
        anuncioActualizado.setPrecio(anuncioDTO.getPrecio());
        
        when(anuncioService.cambiarEstado(eq(1L), eq("ARCHIVED"))).thenReturn(anuncioActualizado);

        // Act & Assert
        mockMvc.perform(patch("/api/anuncios/1/estado")
                .param("estado", "ARCHIVED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado", is("ARCHIVED")));
    }

    @Test
    void cambiarEstado_ConEstadoInvalido_DeberiaRetornarBadRequest() throws Exception {
        // Arrange
        when(anuncioService.cambiarEstado(eq(1L), eq("ESTADO_INVALIDO")))
                .thenThrow(new AnuncioValidationException("Estado no válido: ESTADO_INVALIDO"));

        // Act & Assert
        mockMvc.perform(patch("/api/anuncios/1/estado")
                .param("estado", "ESTADO_INVALIDO"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", containsString("Estado no válido")));
    }

    @Test
    void agregarTagAAnuncio_CuandoExistenAmbos_DeberiaRetornarAnuncioConTag() throws Exception {
        // Arrange
        anuncioDTO.setTags(new HashSet<>(tagDTOList));
        when(anuncioService.agregarTagAAnuncio(eq(1), eq(1))).thenReturn(anuncioDTO);

        // Act & Assert
        mockMvc.perform(post("/api/anuncios/1/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags[*].nombre", containsInAnyOrder("Java", "Programación")));
    }

    @Test
    void obtenerTagsDeAnuncio_CuandoExisteAnuncio_DeberiaRetornarListaTags() throws Exception {
        // Arrange
        when(anuncioService.obtenerTagsDeAnuncio(eq(1))).thenReturn(tagDTOList);

        // Act & Assert
        mockMvc.perform(get("/api/anuncios/1/tags"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].nombre", is("Java")))
                .andExpect(jsonPath("$[1].nombre", is("Programación")));
    }

    @Test
    void eliminarTagDeAnuncio_CuandoExistenAmbos_DeberiaRetornarAnuncioSinTag() throws Exception {
        // Arrange
        AnuncioDTO anuncioSinTag = new AnuncioDTO();
        anuncioSinTag.setId(1);
        anuncioSinTag.setUserId(anuncioDTO.getUserId());
        anuncioSinTag.setTitulo(anuncioDTO.getTitulo());
        anuncioSinTag.setDescripcion(anuncioDTO.getDescripcion());
        anuncioSinTag.setAreaEspecializacion(anuncioDTO.getAreaEspecializacion());
        anuncioSinTag.setPrecio(anuncioDTO.getPrecio());
        anuncioSinTag.setEstado(anuncioDTO.getEstado());
        // No incluimos ningún tag
        
        when(anuncioService.eliminarTagDeAnuncio(eq(1), eq(1))).thenReturn(anuncioSinTag);

        // Act & Assert
        mockMvc.perform(delete("/api/anuncios/1/tags/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tags", hasSize(0)));
    }
}
