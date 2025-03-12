package com.hazmelaucb.ms_rating.controller;

import com.hazmelaucb.ms_rating.model.dto.RatingResponseDTO;
import com.hazmelaucb.ms_rating.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingRepository ratingRepository;

    @GetMapping
    public List<RatingResponseDTO> getAllRatings(){
        return ratingRepository.findAll().stream()
                .map(RatingResponseDTO::new)
                .collect(Collectors.toList());
    }
}
