package com.kawsay.ia.controllers;

import com.kawsay.ia.entity.Reporte;
import com.kawsay.ia.repository.ReporteRepository;
import com.kawsay.ia.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping("/api/reportes/all")
    public List<Reporte> findAllReporteService(){
        return reporteService.findAllReporteService();
    }
}
