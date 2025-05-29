package com.hazmelaucb.ms_rating.api;

import com.hazmelaucb.ms_rating.model.dto.RatingRequestDTO;
import com.hazmelaucb.ms_rating.bl.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/rating")
@Tag(name = "Rating", description = "REST API para calificaciones")
public class RatingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatingController.class);
    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "${api.rating.get-all.description}", description = "${api.rating.get-all.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}") })
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<RatingRequestDTO>> getAllRatings() {
        LOGGER.info("Obteniendo todas las calificaciones");

        List<RatingRequestDTO> ratings = ratingService.getAllRatings();
        return ratings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ratings);
    }

    @Operation(summary = "${api.rating.get-rating-by-id.description}", description = "${api.rating.get-rating-by-id.notes}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "${api.responseCodes.ok.description}"),
            @ApiResponse(responseCode = "404", description = "${api.responseCodes.notFound.description}") })
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<RatingRequestDTO> getRatingById(
            @Parameter(description = "${api.rating.get-rating-by-id.parameters.id}", required = true) @PathVariable("id") Long id) {
        LOGGER.info("Obteniendo calificación con id: {}", id);

        RatingRequestDTO rating = ratingService.getRatingById(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "${api.rating.get-ratings-by-user-id.description}", description = "${api.rating.get-ratings-by-user-id.notes}")
    @GetMapping(value = "/user/{userId}", produces = "application/json")
    public ResponseEntity<List<RatingRequestDTO>> getRatingsByUserId(@PathVariable Long userId) {
        LOGGER.info("Obteniendo calificaciones del usuario con id: {}", userId);

        List<RatingRequestDTO> ratings = ratingService.getRatingsByUserId(userId);
        return ratings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ratings);
    }

    @Operation(summary = "${api.rating.get-ratings-by-ad-id.description}", description = "${api.rating.get-ratings-by-ad-id.notes}")
    @GetMapping(value = "/ad/{adId}", produces = "application/json")
    public ResponseEntity<List<RatingRequestDTO>> getRatingsByAdId(@PathVariable Long adId) {
        LOGGER.info("Obteniendo calificaciones para el anuncio con id: {}", adId);

        List<RatingRequestDTO> ratings = ratingService.getRatingsByAdId(adId);
        return ratings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ratings);
    }

    @Operation(summary = "${api.rating.create-rating.description}", description = "${api.rating.create-rating.notes}")
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<RatingRequestDTO> createRating(@RequestBody RatingRequestDTO ratingRequestDTO) {
        LOGGER.info("Creando una nueva calificación");

        RatingRequestDTO createdRating = ratingService.createRating(ratingRequestDTO);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @Operation(summary = "${api.rating.update-rating.description}", description = "${api.rating.update-rating.notes}")
    @PutMapping(value = "/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<RatingRequestDTO> updateRating(@PathVariable Long id,
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        LOGGER.info("Actualizando calificación con id: {}", id);

        RatingRequestDTO updatedRating = ratingService.updateRating(id, ratingRequestDTO);
        return updatedRating != null ? ResponseEntity.ok(updatedRating) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "${api.rating.delete-rating.description}", description = "${api.rating.delete-rating.notes}")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        LOGGER.info("Eliminando calificación con id: {}", id);

        boolean isDeleted = ratingService.deleteRating(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
