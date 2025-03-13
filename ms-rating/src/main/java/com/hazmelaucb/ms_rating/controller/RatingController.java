package com.hazmelaucb.ms_rating.controller;

import com.hazmelaucb.ms_rating.model.dto.RatingRequestDTO;
import com.hazmelaucb.ms_rating.model.dto.RatingResponseDTO;
import com.hazmelaucb.ms_rating.model.entity.RatingEntity;
import com.hazmelaucb.ms_rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    // Obtener todos los ratings
    @GetMapping
    public List<RatingResponseDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(RatingResponseDTO::new)
                .collect(Collectors.toList());
    }

    // Obtener un rating por ID
    @GetMapping("/{id}")
    public ResponseEntity<RatingResponseDTO> getRatingById(@PathVariable Long id) {
        return ratingRepository.findById(id)
                .map(rating -> ResponseEntity.ok(new RatingResponseDTO(rating)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear un nuevo rating
    @PostMapping
    public ResponseEntity<RatingResponseDTO> createRating(@RequestBody RatingRequestDTO request) {
        RatingEntity newRating = new RatingEntity();
        newRating.setRating(request.getId());
        newRating.setRatedAt(request.getRatedAt() != null ? request.getRatedAt() : ZonedDateTime.now());
        newRating.setUpdatedAt(ZonedDateTime.now());

        RatingEntity savedRating = ratingRepository.save(newRating);
        return ResponseEntity.ok(new RatingResponseDTO(savedRating));
    }

    // Actualizar un rating existente
    @PutMapping("/{id}")
    public ResponseEntity<RatingResponseDTO> updateRating(@PathVariable Long id, @RequestBody RatingRequestDTO request) {
        return ratingRepository.findById(id).map(existingRating -> {
            existingRating.setRating(request.getId());
            existingRating.setUpdatedAt(ZonedDateTime.now());

            RatingEntity updatedRating = ratingRepository.save(existingRating);
            return ResponseEntity.ok(new RatingResponseDTO(updatedRating));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un rating
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteRating(@PathVariable Long id) {
        return ratingRepository.findById(id).map(rating -> {
            ratingRepository.delete(rating);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

