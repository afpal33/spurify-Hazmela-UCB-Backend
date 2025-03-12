package com.hazmelaucb.ms_rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hazmelaucb.ms_rating.model.entity.RatingEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
}
