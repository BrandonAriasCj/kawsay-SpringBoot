package com.kawsay.ia.controllers;

import com.kawsay.ia.entity.Preferencia;
import com.kawsay.ia.service.PreferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/preferencias")
@CrossOrigin(origins = "http://localhost:5173") // Ajusta si usas otra URL en desarrollo
public class PreferenciaController {

    @Autowired
    private PreferenciaService preferenciaService;

    @GetMapping
    public ResponseEntity<Map<String, List<String>>> obtenerPreferenciasPorTipo() {
        List<Preferencia> lista = preferenciaService.findAll();

        Map<String, List<String>> agrupadas = lista.stream()
                .collect(Collectors.groupingBy(
                        Preferencia::getTipo,
                        Collectors.mapping(Preferencia::getValor, Collectors.toList())
                ));

        return ResponseEntity.ok(agrupadas);
    }
}
