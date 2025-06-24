package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.ComentarioTreeConReaccionesDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.service.ComentarioService;
import com.kawsay.ia.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;
    @Autowired
    private ComentarioService comentarioService;


    @PostMapping("/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> agregarComentario(
            @PathVariable Integer publicacionId, @RequestBody ComentarioDTO comentarioDTO) {
        ComentarioDTO nuevoComentario = publicacionService.agregarComentario(publicacionId, comentarioDTO);
        return new ResponseEntity<>(nuevoComentario, HttpStatus.CREATED);
    }

    @PostMapping("/{publicacionId}/reacciones")
    public ResponseEntity<ReaccionDTO> agregarReaccion(
            @PathVariable Integer publicacionId, @RequestBody ReaccionDTO reaccionDTO) {
        ReaccionDTO nuevaReaccion = publicacionService.agregarReaccion(publicacionId, reaccionDTO);
        return new ResponseEntity<>(nuevaReaccion, HttpStatus.CREATED);
    }


    @GetMapping("/{publicacionId}/comentarios/tree")
    public ResponseEntity<List<ComentarioTreeConReaccionesDTO>> obtenerComentariosTree(
            @PathVariable ("publicacionId") Integer publicacionId) {
        return ResponseEntity.ok(comentarioService.obtenerComentariosAnidadosPorPublicacion(publicacionId));
    }



    @PostMapping("/comentarios/{comentarioId}/respuestas")
    public ResponseEntity<ComentarioDTO> responderAComentario(
            @PathVariable Integer comentarioId,
            @RequestBody ComentarioDTO dto) {
        ComentarioDTO respuesta = comentarioService.responderAComentario(comentarioId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}