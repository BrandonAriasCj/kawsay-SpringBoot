package com.kawsay.ia;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.service.UsuarioService;

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
            Integer id = usuario.getId();
            String correo = usuario.getCorreoInstitucional();
            String password = usuario.getContraseña();
            System.out.println("ID: " + id + ", correo: " + correo + ", password: " + password);
            //Alerta de seguridad, usar DTO no entity defrente.
        }
    }
}
