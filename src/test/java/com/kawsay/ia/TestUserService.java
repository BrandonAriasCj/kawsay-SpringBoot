package com.kawsay.ia;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.RolRepository;
import com.kawsay.ia.service.UsuarioService;

import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class TestUserService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolRepository rolRepository;

    @Transactional
    @Rollback
    @Test
    void crear() {
        // Obtener el Rol real desde la base de datos
        Rol rolRef = rolRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        Usuario newUsuario = new Usuario();
        newUsuario.setRol(rolRef);
        newUsuario.setCorreoInstitucional("correo_" + UUID.randomUUID() + "@tec.co.co");
        newUsuario.setContraseña("1234Test!");

        usuarioService.crearUsuario(newUsuario);

        System.out.println("✅ Usuario creado con éxito");
    }

    @Test
    void findAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        for (Usuario usuario : usuarios) {
            System.out.println("🧑‍💻 Usuario ID: " + usuario.getId());
            System.out.println("📧 Correo: " + usuario.getCorreoInstitucional());
            System.out.println("🎭 Rol: " + (usuario.getRol() != null ? usuario.getRol().getDenominacion() : "Sin rol"));
            System.out.println("------------");
        }
    }

}
