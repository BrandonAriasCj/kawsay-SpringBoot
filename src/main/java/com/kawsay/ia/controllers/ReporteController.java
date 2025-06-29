package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.ReporteDTO;
import com.kawsay.ia.entity.Reporte;
import com.kawsay.ia.mapper.ReporteMapper;
import com.kawsay.ia.repository.ReporteRepository;
import com.kawsay.ia.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController()
@RequestMapping()
public class ReporteController {
    @Autowired
    private ReporteService reporteService;
    @Autowired
    private ReporteRepository reporteRepository;

    @GetMapping("/reportes/all")
    public List<ReporteDTO> findAllReporteService(){
        List<Reporte> reportes = reporteRepository.findAll();

        List<ReporteDTO> lista =  reportes.stream()
                .map(ReporteMapper::toDTO)
                .collect(Collectors.toList());
        System.out.println(lista);
        return lista;
    }
}
