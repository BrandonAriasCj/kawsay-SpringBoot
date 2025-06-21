package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.PublicacionDTO;

import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.entity.UsuarioGrupoId;
import com.kawsay.ia.repository.GrupoRepository;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.repository.UsuarioGrupoRepository;
import com.kawsay.ia.repository.UsuarioRepository;
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
    private PublicacionRepository publicacionRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private GrupoRepository grupoRepo;

    @Autowired
    private UsuarioGrupoRepository usuarioGrupoRepo;

    @PostMapping
    public ResponseEntity<?> crearPublicacion(@RequestBody PublicacionDTO dto) {
        boolean estaUnido = usuarioGrupoRepo.existsById(
                new UsuarioGrupoId(dto.autorId, dto.grupoId)
        );

        if (!estaUnido) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("No estás unido al grupo para publicar.");
        }

        Usuario autor = usuarioRepo.findById(dto.autorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Grupo grupo = grupoRepo.findById(dto.grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        Publicacion nueva = new Publicacion(dto.contenido, autor, grupo);
        publicacionRepo.save(nueva);
        return ResponseEntity.ok("Publicación creada con éxito.");
    }

    @GetMapping("/grupo/{grupoId}")
    public List<PublicacionDTO> publicacionesDelGrupo(@PathVariable Integer grupoId) {
        return publicacionRepo.findAll().stream()
                .filter(pub -> pub.getGrupo().getId().equals(grupoId))
                .map(pub -> {
                    PublicacionDTO dto = new PublicacionDTO();
                    dto.id = pub.getId();
                    dto.contenido = pub.getContenido();
                    dto.autorId = pub.getAutor().getId();
                    dto.grupoId = pub.getGrupo().getId();
                    return dto;
                }).toList();
    }



}