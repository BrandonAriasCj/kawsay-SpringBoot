package com.kawsay.ia.userTest;

import com.kawsay.ia.dto.RolTipo;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.RolRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class LimpiezaDatosTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Test
    @Transactional
    @Rollback(false) // Evita que se reviertan los cambios
    public void limpiarDuplicados() {
        limpiarUsuariosPorCorreo("axel.loayza@tecsup.edu.pe");
        limpiarUsuariosPorCorreo("macheigth2018@gmail.com");
        limpiarRolesDuplicados();
    }

    private void limpiarUsuariosPorCorreo(String correo) {
        List<Usuario> usuarios = usuarioRepository.findAllByCorreoInstitucional(correo);
        if (usuarios.size() <= 1) return;

        Usuario primero = usuarios.get(0);
        usuarios.subList(1, usuarios.size()).forEach(usuarioRepository::delete);

        System.out.println("🧹 Usuarios eliminados por correo '" + correo + "': " + (usuarios.size() - 1));
    }

    private void limpiarRolesDuplicados() {
        for (RolTipo tipo : RolTipo.values()) {
            List<Rol> roles = rolRepository.findAllByDenominacion(tipo);
            if (roles.size() <= 1) continue;

            Rol primero = roles.get(0);
            roles.subList(1, roles.size()).forEach(rolRepository::delete);

            System.out.println("🧹 Roles duplicados eliminados para '" + tipo + "': " + (roles.size() - 1));
        }
    }
}
