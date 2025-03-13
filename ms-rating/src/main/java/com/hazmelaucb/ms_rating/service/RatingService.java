package com.hazmelaucb.ms_rating.service;

import com.hazmelaucb.ms_rating.model.dto.RatingRequestDTO;
import com.hazmelaucb.ms_rating.model.dto.RatingResponseDTO;
import com.hazmelaucb.ms_rating.model.entity.RatingEntity;
import com.hazmelaucb.ms_rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RatingResponseDTO getRatingById(Long id) {
        RatingEntity rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + id));
        return convertToDTO(rating);
    }

    @Transactional
    public RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO) {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRating(ratingRequestDTO.getId());
        ratingEntity.setRatedAt(ZonedDateTime.now());
        ratingEntity.setUpdatedAt(ZonedDateTime.now());

        // Save and return the created Rating as DTO
        RatingEntity savedRating = ratingRepository.save(ratingEntity);
        return convertToDTO(savedRating);
    }

    @Transactional
    public RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        RatingEntity existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + id));

        existingRating.setRating(ratingRequestDTO.getId());
        existingRating.setUpdatedAt(ZonedDateTime.now());

        // Save and return the updated Rating as DTO
        RatingEntity updatedRating = ratingRepository.save(existingRating);
        return convertToDTO(updatedRating);
    }

    @Transactional
    public boolean deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new NoSuchElementException("Rating not found with ID: " + id);
        }
        ratingRepository.deleteById(id);
        return false;
    }

    // Helper method to convert RatingEntity to RatingResponseDTO
    private RatingResponseDTO convertToDTO(RatingEntity ratingEntity) {
        return new RatingResponseDTO(ratingEntity.getUpdatedAt(), ratingEntity.getRatedAt(),
                ratingEntity.getRating(), ratingEntity.getId_rating());
    }
}
