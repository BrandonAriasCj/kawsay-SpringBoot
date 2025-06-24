package com.kawsay.ia.builder;

import com.github.javafaker.Faker;
import com.kawsay.ia.dto.GrupoDTO;
import com.kawsay.ia.entity.Publicacion;
import com.kawsay.ia.repository.PublicacionRepository;
import com.kawsay.ia.service.GrupoService;
import com.kawsay.ia.service.PublicacionService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
@Disabled("Factories de prueba")
public class GruposApoyoBuilder {
    @Autowired
    GrupoService grupoService;

    public void GruposFake() {
        Faker faker = new Faker();

        GrupoDTO grupoFake = new GrupoDTO();
        grupoFake.setNombre("Grupo - "+faker.lorem().word());
        grupoFake.setDescripcion(faker.lorem().sentence(4));
        grupoFake.setCategoria(faker.lorem().word());
        grupoFake.setFechaCreacion(LocalDateTime.now());
        grupoFake.setCreadorId(1);
        grupoFake.setModeradorId(1);

        grupoService.crearGrupo(grupoFake);
    }

    @Test
    public void gruposFake() {
        for (int i = 0; i < 10; i++) {
            Faker faker = new Faker();
            Integer idRolRandom1 = ThreadLocalRandom.current().nextInt(1,  10);
            Integer idRolRandom2 = ThreadLocalRandom.current().nextInt(1,  10);

            GrupoDTO grupoFake = new GrupoDTO();
            grupoFake.setNombre("Grupo - "+faker.lorem().word());
            grupoFake.setDescripcion(faker.lorem().sentence(4));
            grupoFake.setCategoria(faker.lorem().word());
            grupoFake.setFechaCreacion(LocalDateTime.now());
            grupoFake.setCreadorId(idRolRandom1);
            grupoFake.setModeradorId(idRolRandom2);

            grupoService.crearGrupo(grupoFake);
        }
    }

    @Autowired
    PublicacionService publicacionService;
    PublicacionRepository publicacionRepository;
    @Test
    public void PostFake() {
        Faker faker = new Faker();
        Publicacion newPost = new Publicacion();
        //Crear el post como se debe
        //publicacionRepository.save();
    }
}
