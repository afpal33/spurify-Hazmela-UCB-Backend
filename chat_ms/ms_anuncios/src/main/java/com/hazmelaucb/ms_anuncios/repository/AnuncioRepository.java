package com.hazmelaucb.ms_anuncios.repository;

import com.hazmelaucb.ms_anuncios.model.entity.Anuncio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AnuncioRepository extends JpaRepository<Anuncio, Long> {
    
    List<Anuncio> findByUserId(Integer userId);
    
    List<Anuncio> findByAreaEspecializacion(String areaEspecializacion);
    
    List<Anuncio> findByEstado(String estado);
    
    List<Anuncio> findByPrecioBetween(BigDecimal precioMin, BigDecimal precioMax);
    
    List<Anuncio> findByAreaEspecializacionAndEstado(String areaEspecializacion, String estado);
}
