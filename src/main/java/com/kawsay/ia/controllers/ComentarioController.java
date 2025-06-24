package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.dto.ComentarioTreeConReaccionesDTO;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.service.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
@CrossOrigin(origins = "http://localhost:5173")
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


}
