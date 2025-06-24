package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.PerfilDTO;
import com.kawsay.ia.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "http://localhost:5173")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<PerfilDTO> getMiPerfil(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        PerfilDTO perfilDTO = perfilService.getPerfilCompleto(email);
        return ResponseEntity.ok(perfilDTO);
    }

    @PutMapping
    public ResponseEntity<PerfilDTO> updateMiPerfil(@AuthenticationPrincipal Jwt jwt, @RequestBody PerfilDTO perfilDTO) {
        String email = jwt.getClaimAsString("email");
        PerfilDTO perfilActualizado = perfilService.updatePerfil(email, perfilDTO);
        return ResponseEntity.ok(perfilActualizado);
    }
}