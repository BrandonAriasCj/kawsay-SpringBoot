package com.kawsay.user.controller;

import com.kawsay.user.service.UsuarioService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api")
public class UsuarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioController.class);
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuario")
    public ResponseEntity<String> recibirUsuario(HttpServletRequest request) {
        LOGGER.info("Recibiendo petición POST en /api/usuario");

        try {
            String authHeader = request.getHeader("Authorization");
            LOGGER.info("Authorization header recibido: {}", authHeader);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                LOGGER.warn("Token no proporcionado o formato incorrecto en Header");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token no proporcionado");
            }

            String token = authHeader.replace("Bearer ", "");
            LOGGER.debug("Token extraído: {}", token);

            // Extraer claims del token
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            LOGGER.info("Token parseado correctamente, claims: {}", claims.toJSONObject());

            String id = claims.getSubject(); // sub
            String email = claims.getStringClaim("email");
            LOGGER.info("Extraído id: {} y email: {}", id, email);

            usuarioService.guardarOActualizar(id, email);
            LOGGER.info("Usuario procesado correctamente con id: {}", id);

            return ResponseEntity.ok("✅ Usuario registrado o actualizado correctamente");

        } catch (Exception e) {
            LOGGER.error("Error procesando la petición: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error procesando el token: " + e.getMessage());
        }
    }
}
