package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.GrupoDTO;
import com.kawsay.ia.dto.PublicacionDTO;
import com.kawsay.ia.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@CrossOrigin(origins = "http://localhost:5173")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> obtenerTodosLosGrupos() {
        return ResponseEntity.ok(grupoService.obtenerTodosLosGrupos());
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> crearGrupo(@RequestBody GrupoDTO grupoDTO) {
        GrupoDTO nuevoGrupo = grupoService.crearGrupo(grupoDTO);
        return new ResponseEntity<>(nuevoGrupo, HttpStatus.CREATED);
    }


    @GetMapping("/{id}/publicaciones")
    public ResponseEntity<List<PublicacionDTO>> obtenerPublicacionesDeGrupo(@PathVariable Integer id) {
        return ResponseEntity.ok(grupoService.obtenerPublicacionesDeGrupo(id));
    }

    @PostMapping("/{id}/publicaciones")
    public ResponseEntity<PublicacionDTO> crearPublicacionEnGrupo(
            @PathVariable Integer id, @RequestBody PublicacionDTO publicacionDTO) {
        PublicacionDTO nuevaPublicacion = grupoService.crearPublicacionEnGrupo(id, publicacionDTO);
        return new ResponseEntity<>(nuevaPublicacion, HttpStatus.CREATED);
    }
}