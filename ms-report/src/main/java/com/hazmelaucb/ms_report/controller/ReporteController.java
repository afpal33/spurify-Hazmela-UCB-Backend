package com.hazmelaucb.ms_report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hazmelaucb.ms_report.model.Reporte;

import com.hazmelaucb.ms_report.service.ReporteService;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    // Endpoint para crear un reporte
    @PostMapping
    public Reporte reportar(@RequestBody Reporte reporte) {
        return reporteService.crearReporte(reporte);
    }
}
