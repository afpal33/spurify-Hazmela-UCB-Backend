package com.hazmelaucb.ms_rating.api;

import com.hazmelaucb.ms_rating.model.dto.RatingRequestDTO;
import com.hazmelaucb.ms_rating.bl.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Rating", description = "API for managing ratings")
public class RatingController {

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "Get all ratings", description = "Returns a list of all ratings")
    @GetMapping
    public ResponseEntity<List<RatingRequestDTO>> getAllRatings() {
        List<RatingRequestDTO> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Get rating by ID", description = "Returns a rating by the provided ID")
    @GetMapping("/{id}")
    public ResponseEntity<RatingRequestDTO> getRatingById(@PathVariable Long id) {
        RatingRequestDTO rating = ratingService.getRatingById(id);
        return rating != null ? ResponseEntity.ok(rating) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Get ratings by user ID", description = "Returns all ratings given by a specific user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RatingRequestDTO>> getRatingsByUserId(@PathVariable Long userId) {
        List<RatingRequestDTO> ratings = ratingService.getRatingsByUserId(userId);
        return ratings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Get ratings by ad ID", description = "Returns all ratings for a specific advertisement")
    @GetMapping("/ad/{adId}")
    public ResponseEntity<List<RatingRequestDTO>> getRatingsByAdId(@PathVariable Long adId) {
        List<RatingRequestDTO> ratings = ratingService.getRatingsByAdId(adId);
        return ratings.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Create a new rating", description = "Creates a new rating")
    @PostMapping
    public ResponseEntity<RatingRequestDTO> createRating(@RequestBody RatingRequestDTO ratingRequestDTO) {
        RatingRequestDTO createdRating = ratingService.createRating(ratingRequestDTO);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing rating", description = "Updates the information of an existing rating")
    @PutMapping("/{id}")
    public ResponseEntity<RatingRequestDTO> updateRating(
            @PathVariable Long id,
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        RatingRequestDTO updatedRating = ratingService.updateRating(id, ratingRequestDTO);
        return updatedRating != null ? ResponseEntity.ok(updatedRating) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete a rating", description = "Deletes a rating by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
        boolean isDeleted = ratingService.deleteRating(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
