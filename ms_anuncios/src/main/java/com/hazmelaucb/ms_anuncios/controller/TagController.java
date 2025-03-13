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
    
    @Operation(summary = "Obtener todos los tags", description = "${api.tag.getAll.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "500", description = "${api.responseCodes.serverError.description}")
    })
    @GetMapping
    public ResponseEntity<List<TagDTO>> obtenerTodos() {
        return ResponseEntity.ok(tagService.buscarTodos());
    }
    
    @Operation(summary = "Obtener tag por ID", description = "${api.tag.getById.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> obtenerPorId(
            @Parameter(description = "ID del tag a obtener", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(tagService.buscarPorId(id));
    }
    
    @Operation(summary = "Buscar tag por nombre exacto", description = "${api.tag.getByName.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<TagDTO> obtenerPorNombre(
            @Parameter(description = "Nombre exacto del tag", required = true)
            @PathVariable String nombre) {
        return ResponseEntity.ok(tagService.buscarPorNombre(nombre));
    }
    
    @Operation(summary = "Buscar tags por coincidencia de nombre", description = "${api.tag.searchByText.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}")
    })
    @GetMapping("/buscar")
    public ResponseEntity<List<TagDTO>> buscarPorCoincidencia(
            @Parameter(description = "Texto parcial para la búsqueda", required = true)
            @RequestParam String texto) {
        return ResponseEntity.ok(tagService.buscarPorCoincidencia(texto));
    }
    
    @Operation(summary = "Crear nuevo tag", description = "${api.tag.create.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}")
    })
    @PostMapping
    public ResponseEntity<TagDTO> crear(@Valid @RequestBody TagDTO tagDTO) {
        TagDTO nuevoTag = tagService.crear(tagDTO);
        return new ResponseEntity<>(nuevoTag, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Actualizar tag", description = "${api.tag.update.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> actualizar(
            @Parameter(description = "ID del tag a actualizar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody TagDTO tagDTO) {
        return ResponseEntity.ok(tagService.actualizar(id, tagDTO));
    }
    
    @Operation(summary = "Eliminar tag", description = "${api.tag.delete.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.noContent.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del tag a eliminar", required = true)
            @PathVariable Long id) {
        tagService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Verificar existencia de tag por nombre", description = "${api.tag.exists.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}")
    })
    @GetMapping("/existe")
    public ResponseEntity<Boolean> existePorNombre(
            @Parameter(description = "Nombre del tag a verificar", required = true)
            @RequestParam String nombre) {
        return ResponseEntity.ok(tagService.existePorNombre(nombre));
    }
}
