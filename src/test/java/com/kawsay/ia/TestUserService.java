package com.kawsay.ia;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.service.UsuarioService;

import org.checkerframework.checker.fenum.qual.SwingTextOrientation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestUserService {
    @Autowired
    private UsuarioService usuarioService;
    @Test
    void findAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAllUsuarios();
        for (Usuario usuario : usuarios) {
            System.out.println(usuario.toString());
        }
    }
    @Test
    void crear() {
        //Nota: ejecutar luego de que este creado algun rol
        Rol rolRef = new Rol();
        rolRef.setId(1);

        Usuario newUsuario = new Usuario();
        newUsuario.setRol(rolRef);
        newUsuario.setCorreoInstitucional("correoG2@tec.co.co");
        newUsuario.setContraseña("#$%&/");
        usuarioService.crearUsuario(newUsuario);

    }
}
