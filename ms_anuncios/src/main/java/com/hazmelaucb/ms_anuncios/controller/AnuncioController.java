package com.hazmelaucb.ms_anuncios.controller;

import com.hazmelaucb.ms_anuncios.exception.GlobalExceptionHandler.ErrorResponse;
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
import java.time.LocalDateTime;
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
    
    @Operation(summary = "Obtener todos los anuncios", description = "${api.anuncio.getAll.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "500", description = "${api.responseCodes.serverError.description}")
    })
    @GetMapping
    public ResponseEntity<List<AnuncioDTO>> obtenerTodos() {
        return ResponseEntity.ok(anuncioService.buscarTodos());
    }
    
    @Operation(summary = "Obtener anuncio por ID", description = "${api.anuncio.getById.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AnuncioDTO> obtenerPorId(
            @Parameter(description = "ID del anuncio a obtener", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(anuncioService.buscarPorId(id));
    }
    
    @Operation(summary = "Obtener anuncios por usuario", description = "${api.anuncio.getByUser.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorUsuario(
            @Parameter(description = "ID del usuario", required = true)
            @PathVariable Integer userId) {
        return ResponseEntity.ok(anuncioService.buscarPorUsuario(userId));
    }
    
    @Operation(summary = "Obtener anuncios por área", description = "${api.anuncio.getByArea.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/area/{area}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorArea(
            @Parameter(description = "Área de especialización", required = true)
            @PathVariable String area) {
        return ResponseEntity.ok(anuncioService.buscarPorAreaEspecializacion(area));
    }
    
    @Operation(summary = "Obtener anuncios por estado", description = "${api.anuncio.getByStatus.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorEstado(
            @Parameter(description = "Estado del anuncio", required = true)
            @PathVariable String estado) {
        return ResponseEntity.ok(anuncioService.buscarPorEstado(estado));
    }
    
    @Operation(summary = "Obtener anuncios por rango de precio", description = "${api.anuncio.getByPriceRange.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/precio")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorRangoPrecio(
            @Parameter(description = "Precio mínimo", required = false)
            @RequestParam(defaultValue = "0.0") BigDecimal min,
            @Parameter(description = "Precio máximo", required = false)
            @RequestParam(defaultValue = "999999.99") BigDecimal max) {
        return ResponseEntity.ok(anuncioService.buscarPorRangoPrecio(min, max));
    }
    
    @Operation(summary = "Crear nuevo anuncio", description = "${api.anuncio.create.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "${api.responseCodes.created.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}")
    })
    @PostMapping
    public ResponseEntity<AnuncioDTO> crear(@Valid @RequestBody AnuncioCrearDTO anuncioDTO) {
        AnuncioDTO nuevoAnuncio = anuncioService.crear(anuncioDTO);
        return new ResponseEntity<>(nuevoAnuncio, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Actualizar anuncio", description = "${api.anuncio.update.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AnuncioDTO> actualizar(
            @Parameter(description = "ID del anuncio a actualizar", required = true)
            @PathVariable Long id, 
            @Valid @RequestBody AnuncioCrearDTO anuncioDTO) {
        return ResponseEntity.ok(anuncioService.actualizar(id, anuncioDTO));
    }
    
    @Operation(summary = "Eliminar anuncio", description = "${api.anuncio.delete.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "${api.responseCodes.noContent.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del anuncio a eliminar", required = true)
            @PathVariable Long id) {
        anuncioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Cambiar estado de anuncio", description = "${api.anuncio.changeStatus.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.stateUpdated.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<AnuncioDTO> cambiarEstado(
            @Parameter(description = "ID del anuncio", required = true)
            @PathVariable Long id, 
            @Parameter(description = "Nuevo estado", required = true)
            @RequestParam String estado) {
        return ResponseEntity.ok(anuncioService.cambiarEstado(id, estado));
    }
    
    @Operation(summary = "Agregar tag a un anuncio", description = "${api.anuncio.addTagToAnuncio.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @PostMapping("/{anuncioId}/tags/{tagId}")
    public ResponseEntity<AnuncioDTO> agregarTagAAnuncio(
            @Parameter(description = "ID del anuncio", required = true) @PathVariable Integer anuncioId,
            @Parameter(description = "ID del tag a agregar", required = true) @PathVariable Integer tagId) {
        AnuncioDTO anuncioActualizado = anuncioService.agregarTagAAnuncio(anuncioId, tagId);
        return ResponseEntity.ok(anuncioActualizado);
    }
    
    @Operation(summary = "Obtener tags de un anuncio", description = "${api.anuncio.getTagsByAnuncioId.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}")
    })
    @GetMapping("/{anuncioId}/tags")
    public ResponseEntity<List<TagDTO>> obtenerTagsDeAnuncio(
            @Parameter(description = "ID del anuncio", required = true) @PathVariable Integer anuncioId) {
        List<TagDTO> tags = anuncioService.obtenerTagsDeAnuncio(anuncioId);
        return ResponseEntity.ok(tags);
    }
    
    @Operation(summary = "Eliminar tag de un anuncio", description = "${api.anuncio.removeTagFromAnuncio.description}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}"),
            @ApiResponse(responseCode = "400", description = "${api.responseCodes.badRequest.description}")
    })
    @DeleteMapping("/{anuncioId}/tags/{tagId}")
    public ResponseEntity<AnuncioDTO> eliminarTagDeAnuncio(
            @Parameter(description = "ID del anuncio", required = true) @PathVariable Integer anuncioId,
            @Parameter(description = "ID del tag a eliminar", required = true) @PathVariable Integer tagId) {
        AnuncioDTO anuncioActualizado = anuncioService.eliminarTagDeAnuncio(anuncioId, tagId);
        return ResponseEntity.ok(anuncioActualizado);
    }
}
