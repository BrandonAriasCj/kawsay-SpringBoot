package com.kawsay.ia.service;
import com.kawsay.ia.dto.RolTipo;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.mapper.RegistroUsuarioLock;
import com.kawsay.ia.repository.RolRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private RegistroUsuarioLock registroUsuarioLock;

    public List<Usuario> findAllUsuarios() {

        return usuarioRepository.findAll();
    }

    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }




    @Transactional
    public Usuario registrarSiNoExiste(String correoInstitucional) {
        Object lock = registroUsuarioLock.obtenerLock(correoInstitucional);

        synchronized (lock) {
            try {
                return usuarioRepository.findByCorreoInstitucional(correoInstitucional)
                        .orElseGet(() -> {
                            Rol rol = rolRepository.findByDenominacion(RolTipo.ESTUDIANTE)
                                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

                            Usuario nuevo = Usuario.builder()
                                    .correoInstitucional(correoInstitucional)
                                    .contraseña("externo")
                                    .rol(rol)
                                    .build();

                            return usuarioRepository.save(nuevo);
                        });
            } catch (DataIntegrityViolationException ex) {
                return usuarioRepository.findByCorreoInstitucional(correoInstitucional)
                        .orElseThrow(() -> new RuntimeException("Error tras conflicto"));
            } finally {
                registroUsuarioLock.liberarLock(correoInstitucional);
            }
        }
    }


    public Usuario registrarDesdeToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("No autenticado");
        }

        Jwt jwt = (Jwt) auth.getPrincipal();
        String correo = jwt.getClaimAsString("email");

        return registrarSiNoExiste(correo);
    }


}
