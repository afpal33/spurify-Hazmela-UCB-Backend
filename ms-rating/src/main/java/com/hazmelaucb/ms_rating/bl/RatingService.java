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

    @Transactional
    public RatingRequestDTO createRating(RatingRequestDTO ratingRequestDTO) {
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setIdAnuncio(ratingRequestDTO.getIdAnuncio());
        ratingEntity.setIdUsuario(ratingRequestDTO.getIdUsuario());
        ratingEntity.setRating(ratingRequestDTO.getRating());
        ratingEntity.setRatedAt(ZonedDateTime.now().toLocalDateTime());
        ratingEntity.setUpdatedAt(ZonedDateTime.now().toLocalDateTime());

        RatingEntity savedRating = ratingRepository.save(ratingEntity);
        return convertToDTO(savedRating);
    }

    @Transactional
    public RatingRequestDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        RatingEntity existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rating not found with ID: " + id));

        existingRating.setRating(ratingRequestDTO.getRating());
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
                ratingEntity.getRating(),
                ratingEntity.getIdAnuncio(),
                ratingEntity.getIdUsuario(),
                ratingEntity.getRatedAt(),
                ratingEntity.getUpdatedAt()
        );
    }
}
