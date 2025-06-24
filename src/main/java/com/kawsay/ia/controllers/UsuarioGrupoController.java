package com.kawsay.ia.controllers;


import com.kawsay.ia.config.AuthUtils;
import com.kawsay.ia.dto.GrupoDTO;
import com.kawsay.ia.dto.SolicitudUnionDTO;
import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.GrupoRepository;
import com.kawsay.ia.repository.ReaccionRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.service.ReaccionService;
import com.kawsay.ia.service.UsuarioGrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class UsuarioGrupoController {

    private final GrupoRepository grupoRepo;
    private final UsuarioGrupoService usuarioGrupoService;
    private final AuthUtils authUtils;
    private final ReaccionService reaccionService;


    @GetMapping("/usuario/creados")
    public List<GrupoDTO> obtenerGruposCreadosPorElUsuario() {
        Usuario usuario = authUtils.getUsuarioAutenticado();
        List<Grupo> creados = grupoRepo.findByCreador(usuario);

        return creados.stream()
                .map(grupo -> {
                    GrupoDTO dto = new GrupoDTO();
                    dto.setId(grupo.getId());
                    dto.setNombre(grupo.getNombre());
                    dto.setDescripcion(grupo.getDescripcion());
                    dto.setCategoria(grupo.getCategoria());
                    dto.setCreadorId(grupo.getCreador().getId());
                    dto.setModeradorId(grupo.getModerador() != null ? grupo.getModerador().getId() : null);
                    dto.setFechaCreacion(grupo.getFechaCreacion());
                    return dto;
                }).toList();
    }

    @PostMapping("/unirse")
    public ResponseEntity<?> unirseAGrupo(@RequestBody SolicitudUnionDTO solicitud) {
        Usuario usuario = authUtils.getUsuarioAutenticado();

        Grupo grupo = grupoRepo.findById(solicitud.grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        usuarioGrupoService.unirseAGrupo(usuario, grupo);
        return ResponseEntity.ok("Usuario unido al grupo.");
    }

    @GetMapping("/usuario/mis-grupos")
    public List<GrupoDTO> obtenerGruposDelUsuarioAutenticado() {
        return usuarioGrupoService.obtenerGruposDelUsuarioActual()
                .stream()
                .map(grupo -> {
                    GrupoDTO dto = new GrupoDTO();
                    dto.setId(grupo.getId());
                    dto.setNombre(grupo.getNombre());
                    dto.setDescripcion(grupo.getDescripcion());
                    dto.setCreadorId(grupo.getCreador().getId());
                    return dto;
                }).toList();
    }


    @PostMapping("/publicacion/{id}/toggle")
    public ResponseEntity<String> reaccionar(@PathVariable Integer id, @RequestParam String tipo) {
        String mensaje = reaccionService.toggleReaccion(id, tipo);
        return ResponseEntity.ok(mensaje);
    }

}
