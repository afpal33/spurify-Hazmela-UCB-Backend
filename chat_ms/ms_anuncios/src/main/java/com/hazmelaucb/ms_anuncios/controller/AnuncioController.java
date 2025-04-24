package com.hazmelaucb.ms_anuncios.controller;

import com.hazmelaucb.ms_anuncios.model.dto.AnuncioCrearDTO;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioDTO;
import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.service.AnuncioService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
@Tag(name = "Anuncio", description = "API para la gestión de anuncios en la plataforma")
public class AnuncioController {

    private final AnuncioService anuncioService;
    
    @Autowired
    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }
    
    @Operation(summary = "Obtener todos los anuncios", description = "Retorna una lista con todos los anuncios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error en el servidor")
    })
    @GetMapping
    public ResponseEntity<List<AnuncioDTO>> obtenerTodos() {
        return ResponseEntity.ok(anuncioService.buscarTodos());
    }
    
    @Operation(summary = "Obtener anuncio por ID", description = "Retorna un anuncio según el ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncio no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnuncioDTO> obtenerPorId(
            @Parameter(description = "ID del anuncio a obtener", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(anuncioService.buscarPorId(id));
    }
    
    @Operation(summary = "Obtener anuncios por usuario", description = "Retorna una lista de anuncios asociados a un usuario específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncios no encontrados para este usuario")
    })
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Integer userId) {
        return ResponseEntity.ok(anuncioService.buscarPorUsuario(userId));
    }
    
    @Operation(summary = "Obtener anuncios por área", description = "Retorna una lista de anuncios filtrados por área de especialización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncios no encontrados para esta área")
    })
    @GetMapping("/area/{area}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorArea(
            @Parameter(description = "Área de especialización", required = true)
            @PathVariable String area) {
        return ResponseEntity.ok(anuncioService.buscarPorAreaEspecializacion(area));
    }
    
    @Operation(summary = "Obtener anuncios por estado", description = "Retorna una lista de anuncios filtrados por estado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncios no encontrados para este estado")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del anuncio", required = true)
            @PathVariable String estado) {
        return ResponseEntity.ok(anuncioService.buscarPorEstado(estado));
    }
    
    @Operation(summary = "Obtener anuncios por rango de precio", description = "Retorna una lista de anuncios dentro de un rango de precios específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "No hay anuncios en este rango de precios")
    })
    @GetMapping("/precio")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorRangoPrecio(
            @Parameter(description = "Precio mínimo", required = false)
            @RequestParam(defaultValue = "0.0") BigDecimal min,
            @Parameter(description = "Precio máximo", required = false)
            @RequestParam(defaultValue = "999999.99") BigDecimal max) {
        return ResponseEntity.ok(anuncioService.buscarPorRangoPrecio(min, max));
    }
    
    @Operation(summary = "Crear nuevo anuncio", description = "Crea un nuevo anuncio en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Anuncio creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<AnuncioDTO> crear(@Valid @RequestBody AnuncioCrearDTO anuncioDTO) {
        AnuncioDTO nuevoAnuncio = anuncioService.crear(anuncioDTO);
        return new ResponseEntity<>(nuevoAnuncio, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Actualizar anuncio", description = "Actualiza la información de un anuncio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anuncio actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Anuncio no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AnuncioDTO> actualizar(
            @Parameter(description = "ID del anuncio a actualizar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody AnuncioCrearDTO anuncioDTO) {
        return ResponseEntity.ok(anuncioService.actualizar(id, anuncioDTO));
    }
    
    @Operation(summary = "Eliminar anuncio", description = "Elimina un anuncio existente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Anuncio eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Anuncio no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del anuncio a eliminar", required = true)
            @PathVariable Long id) {
        anuncioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Cambiar estado de anuncio", description = "Actualiza el estado de un anuncio existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Anuncio no encontrado")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<AnuncioDTO> cambiarEstado(
            @Parameter(description = "ID del anuncio", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevo estado", required = true)
            @RequestParam String estado) {
        return ResponseEntity.ok(anuncioService.cambiarEstado(id, estado));
    }
    
    @Operation(summary = "Agregar tag a un anuncio", description = "Asocia una etiqueta existente a un anuncio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncio o tag no encontrado")
    })
    @PostMapping("/{anuncioId}/tags/{tagId}")
    public ResponseEntity<AnuncioDTO> agregarTagAAnuncio(
            @Parameter(description = "ID del anuncio", required = true) @PathVariable Integer anuncioId,
            @Parameter(description = "ID del tag a agregar", required = true) @PathVariable Integer tagId) {
        AnuncioDTO anuncioActualizado = anuncioService.agregarTagAAnuncio(anuncioId, tagId);
        return ResponseEntity.ok(anuncioActualizado);
    }
    
    @Operation(summary = "Obtener tags de un anuncio", description = "Retorna todas las etiquetas asociadas a un anuncio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncio no encontrado")
    })
    @GetMapping("/{anuncioId}/tags")
    public ResponseEntity<List<TagDTO>> obtenerTagsDeAnuncio(
            @Parameter(description = "ID del anuncio", required = true) @PathVariable Integer anuncioId) {
        List<TagDTO> tags = anuncioService.obtenerTagsDeAnuncio(anuncioId);
        return ResponseEntity.ok(tags);
    }
    
    @Operation(summary = "Eliminar tag de un anuncio", description = "Elimina la asociación entre una etiqueta y un anuncio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Anuncio o tag no encontrado"),
            @ApiResponse(responseCode = "400", description = "Etiqueta no asociada al anuncio")
    })
    @DeleteMapping("/{anuncioId}/tags/{tagId}")
    public ResponseEntity<AnuncioDTO> eliminarTagDeAnuncio(
            @Parameter(description = "ID del anuncio", required = true) @PathVariable Integer anuncioId,
            @Parameter(description = "ID del tag a eliminar", required = true) @PathVariable Integer tagId) {
        AnuncioDTO anuncioActualizado = anuncioService.eliminarTagDeAnuncio(anuncioId, tagId);
        return ResponseEntity.ok(anuncioActualizado);
    }
}
