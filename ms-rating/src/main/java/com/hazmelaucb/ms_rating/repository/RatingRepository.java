package com.hazmelaucb.ms_rating.repository;

import com.hazmelaucb.ms_rating.model.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    List<RatingEntity> findByIdAnuncio(Long idAnuncio);
    List<RatingEntity> findByIdUsuario(Long idUsuario);
}
