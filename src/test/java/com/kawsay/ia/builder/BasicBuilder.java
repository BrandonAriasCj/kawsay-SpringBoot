package com.kawsay.ia.builder;

import com.kawsay.ia.dto.RolTipo;
import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Rol;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.AiChatMemoryRepository;
import com.kawsay.ia.repository.RolRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.service.RolService;
import com.kawsay.ia.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.github.javafaker.Faker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootTest
public class BasicBuilder {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private RolService rolService;
    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void buildRoles_Y_AlumnosBase() {
        // Roles base usando RolTipo
        Rol rolEstudiante = Rol.builder()
                .denominacion(RolTipo.ESTUDIANTE)
                .estado(true)
                .build();
        Rol rolPsicologo = Rol.builder()
                .denominacion(RolTipo.PSICOLOGO)
                .estado(true)
                .build();
        Rol rolModerador = Rol.builder()
                .denominacion(RolTipo.MODERADOR)
                .estado(true)
                .build();

        rolService.crear(rolEstudiante);
        rolService.crear(rolPsicologo);
        rolService.crear(rolModerador);

        Usuario estudiante01 = Usuario.builder()
                .correoInstitucional("william.raul@tecsup.edu.pe")
                .contraseña("12345")
                .rol(rolEstudiante)
                .build();

        Usuario psicologa01 = Usuario.builder()
                .correoInstitucional("gisella.palomino@tecsup.edu.pe")
                .contraseña("256748")
                .rol(rolPsicologo)
                .build();

        Usuario moderador01 = Usuario.builder()
                .correoInstitucional("edwin.sayco@tecsup.edu.pe")
                .contraseña("256748")
                .rol(rolModerador)
                .build();

        usuarioService.crearUsuario(estudiante01);
        usuarioService.crearUsuario(psicologa01);
        usuarioService.crearUsuario(moderador01);
    }

    private Integer idRandom(Integer can) {
        return ThreadLocalRandom.current().nextInt(1, can + 1);
    }

    @Test
    public void UsuariosFake() {
        Faker faker = new Faker();
        List<Rol> roles = rolRepository.findAll();
        Integer can = roles.size();

        for (int i = 0; i < 10; i++) {
            Rol rol = roles.get(ThreadLocalRandom.current().nextInt(can));
            Usuario usuario = Usuario.builder()
                    .correoInstitucional(faker.internet().emailAddress())
                    .contraseña(faker.internet().password())
                    .rol(rol)
                    .build();
            usuarioService.crearUsuario(usuario);
        }
    }

    @Autowired
    private UsuarioRepository userRepo;
    @Autowired
    private AiChatMemoryRepository memoryRepo;

    @Test
    public void AiChatMemoryFake() {
        Faker faker = new Faker();
        List<Usuario> usuarios = userRepo.findAll();
        int can = usuarios.size();

        for (int i = 0; i < 10; i++) {
            AiChatMemory.Type tipo = (i % 2 == 0) ? AiChatMemory.Type.USER : AiChatMemory.Type.ASSISTANT;

            AiChatMemory aiChatMemory = AiChatMemory.builder()
                    .sessionId(faker.code().ean8())
                    .usuario(usuarios.get(ThreadLocalRandom.current().nextInt(can)))
                    .content(faker.lorem().paragraph())
                    .type(tipo)
                    .timestamp(LocalDateTime.now())
                    .build();
            memoryRepo.save(aiChatMemory);
        }
    }

    @Test
    public void Borrador() {
        Faker faker = new Faker();
        List<Usuario> usuarios = usuarioRepository.findAll();
        Usuario usuarioRandom = usuarios.get(ThreadLocalRandom.current().nextInt(usuarios.size()));

        AiChatMemory aiChatMemory = AiChatMemory.builder()
                .sessionId("adfasfas6456")
                .usuario(usuarioRandom)
                .content("asdfadsf")
                .type(AiChatMemory.Type.USER)
                .timestamp(LocalDateTime.now())
                .build();

        System.out.println(faker.name().firstName());
        System.out.println(faker.name().lastName());
        System.out.println(faker.name().fullName());
        System.out.println(faker.name().username());
        System.out.println(faker.lorem().paragraph());
        System.out.println(faker.lorem().sentence());
        System.out.println(String.join("\n", faker.lorem().paragraphs(2)));
        System.out.println(faker.code().ean8());
    }
}
