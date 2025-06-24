package com.kawsay.ia.controllers;

import com.kawsay.ia.dto.UsuarioDTO;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.RolRepository;
import com.kawsay.ia.service.UsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolRepository rolRepository;


    @PostMapping("/token")
    public ResponseEntity<UsuarioDTO> registrar(@AuthenticationPrincipal Jwt jwt) {
        log.info("Claims del JWT: {}", jwt.getClaims()); // <-- Aquí imprime todos los claims

        String correo = jwt.getClaimAsString("email");
        log.info("Claim 'email': {}", correo); // Verifica si está presente

        Usuario usuario = usuarioService.registrarSiNoExiste(correo);

        return ResponseEntity.ok(
                new UsuarioDTO(usuario.getId(), usuario.getCorreoInstitucional(), usuario.getRol().getId())
        );
    }



    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO dto) {
        Rol rol = rolRepository.findById(dto.rolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario nuevo = Usuario.builder()
                .correoInstitucional(dto.correo())
                .contraseña("externo")
                .rol(rol)
                .build();

        usuarioService.crearUsuario(nuevo);

        return new ResponseEntity<>(
                new UsuarioDTO(nuevo.getId(), nuevo.getCorreoInstitucional(), rol.getId()),
                HttpStatus.CREATED
        );
    }


    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        List<UsuarioDTO> lista = usuarioService.findAllUsuarios().stream()
                .map(u -> new UsuarioDTO(u.getId(), u.getCorreoInstitucional(), u.getRol().getId()))
                .toList();

        return ResponseEntity.ok(lista);
    }

}
