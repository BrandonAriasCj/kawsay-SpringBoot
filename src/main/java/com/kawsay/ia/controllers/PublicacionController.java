package com.kawsay.ia.controller;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.request.ComentarioRequestDTO;
import com.kawsay.ia.dto.request.ReaccionRequestDTO;
import com.kawsay.ia.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioDTO> agregarComentario(@PathVariable Integer id, @RequestBody ComentarioRequestDTO dto) {
        return new ResponseEntity<>(publicacionService.agregarComentario(id, dto), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/reacciones")
    public ResponseEntity<Void> agregarReaccion(@PathVariable Integer id, @RequestBody ReaccionRequestDTO dto) {
        publicacionService.agregarReaccion(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}