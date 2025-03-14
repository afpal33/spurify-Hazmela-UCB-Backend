package com.hazmelaucb.ms_report.service;

import com.hazmelaucb.ms_report.model.Reporte;
import com.hazmelaucb.ms_report.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    // Método para crear un reporte
    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }
}
