package com.kawsay.ia;

import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.service.RolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
public class TestRolService {
    @Autowired
    private RolService rolService;

    @Test
    void findAll(){
        List<Rol> listadoRoles = rolService.findAll();
        for (Rol rol : listadoRoles) {
            System.out.println(rol);
        }
    }
    @Test
    void crear() {
        Rol rol = new Rol();
        rol.setDenominacion("Moderador");
        rol.setEstado(true);
        rolService.crear(rol);
        findAll();
    }
}
