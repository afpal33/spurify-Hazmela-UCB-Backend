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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ratings"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @GetMapping
    public ResponseEntity<List<RatingRequestDTO>> getAllRatings() {
        List<RatingRequestDTO> ratings = ratingService.getAllRatings();
        return ResponseEntity.ok(ratings);
    }

    @Operation(summary = "Get rating by ID", description = "Returns a rating by the provided ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the rating"),
            @ApiResponse(responseCode = "404", description = "Rating not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<RatingRequestDTO> getRatingById(
            @Parameter(description = "ID of the rating to retrieve", required = true)
            @PathVariable Long id) {
        RatingRequestDTO rating = ratingService.getRatingById(id);
        if (rating != null) {
            return ResponseEntity.ok(rating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new rating", description = "Creates a new rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the rating"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<RatingRequestDTO> createRating(
            @Parameter(description = "Rating data to be created", required = true)
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        RatingRequestDTO createdRating = ratingService.createRating(ratingRequestDTO);
        return new ResponseEntity<>(createdRating, HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing rating", description = "Updates the information of an existing rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the rating"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Rating not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<RatingRequestDTO> updateRating(
            @Parameter(description = "ID of the rating to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated rating data", required = true)
            @RequestBody RatingRequestDTO ratingRequestDTO) {
        RatingRequestDTO updatedRating = ratingService.updateRating(id, ratingRequestDTO);
        if (updatedRating != null) {
            return ResponseEntity.ok(updatedRating);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a rating", description = "Deletes a rating by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the rating"),
            @ApiResponse(responseCode = "404", description = "Rating not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRating(
            @Parameter(description = "ID of the rating to delete", required = true)
            @PathVariable Long id) {
        boolean isDeleted = ratingService.deleteRating(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
