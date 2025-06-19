package com.kawsay.ia.builder;

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
        //Roles base
        Rol rolEstudiante = Rol.builder()
                .denominacion("ESTUDIANTE")
                .estado(true)
                .build();
        Rol rolPsicologo = Rol.builder()
                .denominacion("PSICOLOGO")
                .estado(true)
                .build();
        Rol rolModerador = Rol.builder()
                .denominacion("MODERADOR")
                .estado(true)
                .build();

        rolService.crear(rolEstudiante);
        rolService.crear(rolPsicologo);
        rolService.crear(rolModerador);


        //Elementos base
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
        Integer idRolRandom = ThreadLocalRandom.current().nextInt(1, can + 1);
        return idRolRandom;
    }

    @Test
    public void UsuariosFake() {
        Faker faker = new Faker();
        Integer can =  rolRepository.findAll().size();

        for (int i = 0; i < 10; i++) {
            Usuario usuario = Usuario.builder()
                    .correoInstitucional(faker.internet().emailAddress())
                    .contraseña(faker.internet().password())
                    .rol(rolRepository.findById(idRandom(can)).orElse(null))
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
        Integer can = userRepo.findAll().size();

        for (int i = 0; i < 10; i++) {
            AiChatMemory aiChatMemory = AiChatMemory.builder()
                    .sessionId(faker.code().ean8())
                    .usuario(userRepo.getById(idRandom(can)))
                    .content(faker.lorem().paragraph())
                    .type(AiChatMemory.Type.USER)
                    .timestamp(LocalDateTime.now())
                    .build();
            memoryRepo.save(aiChatMemory);
        }
    }


    @Autowired
    AiChatMemoryRepository aiChatMemoryRepository;
    @Test
    public void Borrador() {
        Faker faker = new Faker();
        Integer can =  rolRepository.findAll().size();
        Integer idAleatorio = idRandom(can);

        AiChatMemory aiChatMemory = AiChatMemory.builder()
                .sessionId("adfasfas6456")
                .usuario(usuarioRepository.getById(idAleatorio))
                .content("asdfadsf")
                .type(AiChatMemory.Type.USER)
                .timestamp(LocalDateTime.now())
                .build();

        String valor1 = faker.name().firstName();
        String valor2 = faker.name().lastName();
        String valor3 = faker.name().fullName();
        String valor4 = faker.name().username();
        String valor5 = faker.lorem().paragraph();
        String valor6 = faker.lorem().sentence();
        String valor7 = String.valueOf(faker.lorem().paragraphs(2));
        String valor8 = faker.code().ean8();
        //Date valor9 = faker.date().between(LocalDate.now(), LocalDate.now().plusDays(1));

        System.out.println(valor1);
        System.out.println(valor2);
        System.out.println(valor3);
        System.out.println(valor4);
        System.out.println(valor5);
        System.out.println(valor6);
        System.out.println(valor7);
        System.out.println(valor8);
    }

}
