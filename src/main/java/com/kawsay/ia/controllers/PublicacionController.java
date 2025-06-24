package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.PublicacionDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;


    @PostMapping("/grupo/{idGrupo}")
    public ResponseEntity<PublicacionDTO> crearPublicacion(
            @PathVariable Integer idGrupo,
            @RequestBody PublicacionDTO dto) {

        PublicacionDTO nueva = publicacionService.crearPublicacion(idGrupo, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PostMapping("/{id}/comentarios")
    public ResponseEntity<ComentarioDTO> agregarComentario(
            @PathVariable Integer id, @RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO nuevoComentario = publicacionService.agregarComentario(id, comentarioDTO);
        return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED);
    }


    @PostMapping("/{id}/reacciones")
    public ResponseEntity<ReaccionDTO> agregarReaccion(
            @PathVariable Integer id, @RequestBody ReaccionDTO reaccionDTO) {
        ReaccionDTO nuevaReaccion = publicacionService.agregarReaccion(id, reaccionDTO);
        return new ResponseEntity<>(nuevaReaccion, HttpStatus.CREATED);
    }
}