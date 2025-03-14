package com.hazmelaucb.ms_anuncios.repository;

import com.hazmelaucb.ms_anuncios.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByNombre(String nombre);
    
    List<Tag> findByNombreContainingIgnoreCase(String nombre);
    
    boolean existsByNombre(String nombre);
}
