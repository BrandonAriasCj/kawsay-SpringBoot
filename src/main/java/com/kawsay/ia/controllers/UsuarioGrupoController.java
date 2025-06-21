package com.kawsay.ia.controllers;


import com.kawsay.ia.dto.GrupoDTO;
import com.kawsay.ia.dto.SolicitudUnionDTO;
import com.kawsay.ia.entity.Grupo;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.GrupoRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.service.UsuarioGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private GrupoRepository grupoRepo;

    @Autowired
    private UsuarioGrupoService usuarioGrupoService;

    @PostMapping("/unirse")
    public ResponseEntity<?> unirseAGrupo(@RequestBody SolicitudUnionDTO solicitud) {
        Usuario usuario = usuarioRepo.findById(solicitud.usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Grupo grupo = grupoRepo.findById(solicitud.grupoId)
                .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));

        usuarioGrupoService.unirseAGrupo(usuario, grupo);
        return ResponseEntity.ok("Usuario unido al grupo.");
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<GrupoDTO> obtenerGruposDelUsuario(@PathVariable Integer usuarioId) {
        return usuarioGrupoService.obtenerGruposDelUsuario(usuarioId).stream().map(grupo -> {
            GrupoDTO dto = new GrupoDTO();
            dto.id = grupo.getId();
            dto.nombre = grupo.getNombre();
            dto.descripcion = grupo.getDescripcion();
            dto.creadorId = grupo.getCreador().getId();
            return dto;
        }).toList();
    }
}
