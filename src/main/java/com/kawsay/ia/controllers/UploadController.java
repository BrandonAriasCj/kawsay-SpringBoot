package com.kawsay.ia.controllers;

import com.kawsay.ia.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "http://localhost:5173")
public class UploadController {

    @Autowired
    private FileStorageProperties fileStorageProperties;


    @PostMapping("/foto")
    public ResponseEntity<String> subirFoto(@RequestParam("file") MultipartFile file) {
        try {
            // Asegura que el directorio exista
            Path dirPath = Paths.get(fileStorageProperties.getUploadDir());
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            // Genera un nombre único
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String fileName = System.currentTimeMillis() + "_" + originalFilename;

            // Ruta completa
            Path filePath = dirPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Devuelve la URL accesible públicamente
            String url = "/uploads/" + fileName;
            return ResponseEntity.ok(url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al guardar la imagen");
        }
    }


}
