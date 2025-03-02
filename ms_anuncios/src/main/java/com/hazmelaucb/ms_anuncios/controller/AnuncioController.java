package com.hazmelaucb.ms_anuncios.controller;

import com.hazmelaucb.ms_anuncios.model.dto.AnuncioCrearDTO;
import com.hazmelaucb.ms_anuncios.model.dto.AnuncioDTO;
import com.hazmelaucb.ms_anuncios.service.AnuncioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/anuncios")
public class AnuncioController {

    private final AnuncioService anuncioService;
    
    @Autowired
    public AnuncioController(AnuncioService anuncioService) {
        this.anuncioService = anuncioService;
    }
    
    @GetMapping
    public ResponseEntity<List<AnuncioDTO>> obtenerTodos() {
        return ResponseEntity.ok(anuncioService.buscarTodos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<AnuncioDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(anuncioService.buscarPorId(id));
    }
    
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorUsuario(@PathVariable Integer userId) {
        return ResponseEntity.ok(anuncioService.buscarPorUsuario(userId));
    }
    
    @GetMapping("/area/{area}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorArea(@PathVariable String area) {
        return ResponseEntity.ok(anuncioService.buscarPorAreaEspecializacion(area));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(anuncioService.buscarPorEstado(estado));
    }
    
    @GetMapping("/precio")
    public ResponseEntity<List<AnuncioDTO>> obtenerPorRangoPrecio(
            @RequestParam(defaultValue = "0.0") BigDecimal min,
            @RequestParam(defaultValue = "999999.99") BigDecimal max) {
        return ResponseEntity.ok(anuncioService.buscarPorRangoPrecio(min, max));
    }
    
    @PostMapping
    public ResponseEntity<AnuncioDTO> crear(@Valid @RequestBody AnuncioCrearDTO anuncioDTO) {
        AnuncioDTO nuevoAnuncio = anuncioService.crear(anuncioDTO);
        return new ResponseEntity<>(nuevoAnuncio, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AnuncioDTO> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody AnuncioCrearDTO anuncioDTO) {
        return ResponseEntity.ok(anuncioService.actualizar(id, anuncioDTO));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        anuncioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/estado")
    public ResponseEntity<AnuncioDTO> cambiarEstado(
            @PathVariable Long id, 
            @RequestParam String estado) {
        return ResponseEntity.ok(anuncioService.cambiarEstado(id, estado));
    }
}
