package com.hazmelaucb.ms_anuncios.controller;

import com.hazmelaucb.ms_anuncios.model.dto.TagDTO;
import com.hazmelaucb.ms_anuncios.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tags")
@Tag(name = "Tags", description = "API para la gestión de etiquetas de especialización")
public class TagController {

    private final TagService tagService;
    
    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }
    
    @Operation(summary = "Obtener todos los tags", description = "Retorna una lista con todos los tags registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping
    public ResponseEntity<List<TagDTO>> obtenerTodos() {
        return ResponseEntity.ok(tagService.buscarTodos());
    }
    
    @Operation(summary = "Obtener tag por ID", description = "Retorna un tag según el ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Tag no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> obtenerPorId(
            @Parameter(description = "ID del tag a obtener", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(tagService.buscarPorId(id));
    }
    
    @Operation(summary = "Buscar tag por nombre exacto", description = "Retorna un tag con el nombre exacto proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Tag no encontrado")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TagDTO> obtenerPorNombre(
            @Parameter(description = "Nombre exacto del tag", required = true)
            @PathVariable String nombre) {
        return ResponseEntity.ok(tagService.buscarPorNombre(nombre));
    }
    
    @Operation(summary = "Buscar tags por coincidencia de nombre", description = "Retorna una lista de tags que contienen el texto proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<TagDTO>> buscarPorCoincidencia(
            @Parameter(description = "Texto parcial para la búsqueda", required = true)
            @RequestParam String texto) {
        return ResponseEntity.ok(tagService.buscarPorCoincidencia(texto));
    }
    
    @Operation(summary = "Crear nuevo tag", description = "Crea un nuevo tag en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o nombre duplicado")
    })
    @PostMapping
    public ResponseEntity<TagDTO> crear(@Valid @RequestBody TagDTO tagDTO) {
        TagDTO nuevoTag = tagService.crear(tagDTO);
        return new ResponseEntity<>(nuevoTag, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Actualizar tag", description = "Actualiza la información de un tag existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o nombre duplicado"),
            @ApiResponse(responseCode = "404", description = "Tag no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> actualizar(
            @Parameter(description = "ID del tag a actualizar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.actualizar(id, tagDTO));
    }
    
    @Operation(summary = "Eliminar tag", description = "Elimina un tag existente del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Tag no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del tag a eliminar", required = true)
            @PathVariable Long id) {
        tagService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Verificar existencia de tag por nombre", description = "Verifica si ya existe un tag con el nombre proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Respuesta exitosa")
    })
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existePorNombre(
            @Parameter(description = "Nombre del tag a verificar", required = true)
            @RequestParam String nombre) {
        return ResponseEntity.ok(tagService.existePorNombre(nombre));
    }
}
