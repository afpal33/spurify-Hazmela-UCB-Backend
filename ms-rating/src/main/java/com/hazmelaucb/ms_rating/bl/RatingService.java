package com.hazmelaucb.ms_rating.bl;

import com.hazmelaucb.ms_rating.model.dto.RatingRequestDTO;
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
    public List<RatingRequestDTO> getAllRatings() {
        return ratingRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RatingRequestDTO getRatingById(Long id) {
        RatingEntity rating = ratingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + id));
        return convertToDTO(rating);
    }

    @Transactional(readOnly = true)
    public List<RatingRequestDTO> getRatingsByAdId(Long idAnuncio) {
        return ratingRepository.findByIdAnuncio(idAnuncio).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RatingRequestDTO> getRatingsByUserId(Long userId) {
        return ratingRepository.findByIdUsuario(userId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Integer calculateScore(Long rating) {
        if (rating > 5) {
            throw new IllegalArgumentException("El rating no puede ser mayor a 5");
        }
        return switch (rating.intValue()) {
            case 1 -> 0;
            case 2 -> 25;
            case 3 -> 50;
            case 4 -> 80;
            case 5 -> 100;
            default -> throw new IllegalArgumentException("El rating debe estar entre 1 y 5");
        };
    }

    @Transactional
    public RatingRequestDTO createRating(RatingRequestDTO ratingRequestDTO) {
        if (ratingRequestDTO.getRating() > 5) {
            throw new IllegalArgumentException("El rating no puede ser mayor a 5");
        }

        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setIdAnuncio(ratingRequestDTO.getIdAnuncio());
        ratingEntity.setIdUsuario(ratingRequestDTO.getIdUsuario());
        ratingEntity.setRating(ratingRequestDTO.getRating());
        ratingEntity.setScoreAssigned(calculateScore(ratingRequestDTO.getRating()));
        ratingEntity.setRatedAt(ZonedDateTime.now().toLocalDateTime());
        ratingEntity.setUpdatedAt(ZonedDateTime.now().toLocalDateTime());

        RatingEntity savedRating = ratingRepository.save(ratingEntity);
        return convertToDTO(savedRating);
    }

    @Transactional
    public RatingRequestDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        if (ratingRequestDTO.getRating() > 5) {
            throw new IllegalArgumentException("El rating no puede ser mayor a 5");
        }

        RatingEntity existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + id));

        existingRating.setRating(ratingRequestDTO.getRating());
        existingRating.setScoreAssigned(calculateScore(ratingRequestDTO.getRating()));
        existingRating.setUpdatedAt(ZonedDateTime.now().toLocalDateTime());

        RatingEntity updatedRating = ratingRepository.save(existingRating);
        return convertToDTO(updatedRating);
    }

    @Transactional
    public boolean deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new NoSuchElementException("Rating not found with ID: " + id);
        }
        ratingRepository.deleteById(id);
        return true;
    }

    private RatingRequestDTO convertToDTO(RatingEntity ratingEntity) {
        return new RatingRequestDTO(
                ratingEntity.getIdRating(),
                ratingEntity.getRating().longValue(),
                ratingEntity.getIdAnuncio(),
                ratingEntity.getIdUsuario(),
                ratingEntity.getScoreAssigned(),
                ratingEntity.getRatedAt(),
                ratingEntity.getUpdatedAt());
    }
}
