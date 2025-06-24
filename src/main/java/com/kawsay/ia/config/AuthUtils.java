package com.kawsay.ia.config;

import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@Component
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class AuthUtils {

    private final UsuarioRepository usuarioRepository;



    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No hay usuario autenticado en el contexto.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof Jwt jwt) {
            String email = jwt.getClaimAsString("email");

            return usuarioRepository.findByCorreoInstitucional(email)
                    .orElseThrow(() -> new RuntimeException("Usuario con email no registrado: " + email));
        }

        throw new RuntimeException("Token JWT no válido o no soportado.");
    }

    public String getEmailAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt jwt) {
            return jwt.getClaimAsString("email");
        }

        return null;
    }
}
