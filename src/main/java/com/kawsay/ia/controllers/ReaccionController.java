package com.kawsay.ia.controllers;

import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.dto.ReaccionDTO;
import com.kawsay.ia.entity.Reaccion;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.repository.ReaccionRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.repository.PublicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/reacciones")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ReaccionController {

    private final ReaccionRepository reaccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;
    private final AuthUtils authUtils;


    @PostMapping
    public ResponseEntity<String> registrarReaccion(@RequestBody ReaccionDTO dto) {
        Usuario usuario = authUtils.getUsuarioAutenticado();

        Publicacion publicacion = publicacionRepository.findById(dto.publicacionId())
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        Reaccion reaccion = Reaccion.builder()
                .tipo(Reaccion.Tipo.valueOf(dto.tipo().toUpperCase()))
                .usuario(usuario)
                .publicacion(publicacion)
                .build();

        reaccionRepository.save(reaccion);

        return ResponseEntity.ok("Reacción registrada con éxito.");
    }


    @GetMapping("/publicacion/{idPublicacion}")
    public List<ReaccionDTO> obtenerReaccionesPorPublicacion(@PathVariable Integer idPublicacion) {
        return reaccionRepository.findByPublicacionId(idPublicacion)
                .stream()
                .map(r -> new ReaccionDTO(
                        r.getId(),
                        r.getTipo().name(),
                        r.getUsuario().getId(),
                        r.getPublicacion().getId(),
                        r.getFechaReaccion()
                ))
                .toList();
    }


}
