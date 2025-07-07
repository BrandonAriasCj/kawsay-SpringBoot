package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.PerfilPsicologoDTO;
import com.kawsay.ia.service.PerfilPsicologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil-psicologo")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class PerfilPsicologoController {

    @Autowired
    private PerfilPsicologoService perfilPsicologoService;

    @GetMapping
    @PreAuthorize("hasAuthority('PSICOLOGO')")
    public ResponseEntity<PerfilPsicologoDTO> getMiPerfil() {
        return perfilPsicologoService.getMiPerfilPsicologo()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PSICOLOGO')")
    public ResponseEntity<PerfilPsicologoDTO> crearOActualizarPerfil(@RequestBody PerfilPsicologoDTO dto) {
        return ResponseEntity.ok(perfilPsicologoService.crearOActualizarPerfil(dto));
    }
}