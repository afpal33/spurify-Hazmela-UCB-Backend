package com.hazmelaucb.ms_report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hazmelaucb.ms_report.model.Reporte;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
}