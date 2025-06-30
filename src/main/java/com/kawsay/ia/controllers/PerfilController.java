package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.PerfilDTO;
import com.kawsay.ia.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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


    @PostMapping("/foto/actualizar")
    public ResponseEntity<String> subirYActualizarFoto(
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam("file") MultipartFile file
    ) {
        try {
            String email = jwt.getClaimAsString("email");

            // Guardar imagen en servidor
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "_" + originalFilename;
            Path dirPath = Paths.get("uploads");
            if (!Files.exists(dirPath)) Files.createDirectories(dirPath);
            Path filePath = dirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Construir URL relativa
            String url = "/uploads/" + fileName;

            // Actualizar perfil en BD
            perfilService.actualizarFotoPerfil(email, url);

            return ResponseEntity.ok(url); // Devuelve URL para que el frontend la use
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al guardar la imagen");
        }
    }

}