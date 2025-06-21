package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.ComentarioDTO;
import com.kawsay.ia.entity.Comentario;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.ComentarioRepository;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PublicacionRepository publicacionRepo;

    @PostMapping
    public ResponseEntity<?> crearComentario(@RequestBody ComentarioDTO dto) {
        Usuario autor = usuarioRepo.findById(dto.autorId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Publicacion publicacion = publicacionRepo.findById(dto.publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Comentario nuevo = new Comentario(dto.contenido, autor, publicacion);
        comentarioRepo.save(nuevo);

        return ResponseEntity.ok("Comentario publicado con éxito.");
    }

    @GetMapping("/publicacion/{publicacionId}")
    public List<ComentarioDTO> obtenerComentariosPorPublicacion(@PathVariable Integer publicacionId) {
        return comentarioRepo.findAll().stream()
                .filter(c -> c.getPublicacion().getId().equals(publicacionId))
                .map(c -> {
                    ComentarioDTO dto = new ComentarioDTO();
                    dto.id = c.getId();
                    dto.contenido = c.getContenido();
                    dto.autorId = c.getAutor().getId();
                    dto.publicacionId = c.getPublicacion().getId();
                    return dto;
                }).toList();
    }
}
