package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.ComentarioTreeConReaccionesDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping("/{id}/reacciones")
    public ResponseEntity<ReaccionDTO> reaccionarAComentario(
            @PathVariable Integer id,
            @RequestBody ReaccionDTO reaccionDTO) {

        ReaccionDTO nueva = comentarioService.reaccionar(id, reaccionDTO);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @GetMapping("/publicaciones/{id}/comentarios/tree")
    public ResponseEntity<List<ComentarioTreeConReaccionesDTO>> obtenerComentariosTree(@PathVariable Integer id) {
        return ResponseEntity.ok(comentarioService.obtenerComentariosAnidadosPorPublicacion(id));
    }

    @PostMapping("/{id}/respuestas")
    public ResponseEntity<ComentarioDTO> responderAComentario(
            @PathVariable Integer id,
            @RequestBody ComentarioDTO dto) {

        ComentarioDTO respuesta = comentarioService.responderAComentario(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }


}
