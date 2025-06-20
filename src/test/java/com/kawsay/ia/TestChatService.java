package com.kawsay.ia;
import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.AiChatMemoryRepository;
import com.kawsay.ia.service.AiChatMemoryService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TestChatService {
    @Autowired
    private AiChatMemoryService aiChatMemoryService;
    @Autowired
    private AiChatMemoryRepository aiChatMemoryRepository;

    @Test
    void findAll() {
        //assertNotNull(aiChatMemoryService);
        List<AiChatMemory> listaConversacion= aiChatMemoryService.findAllService();
        System.out.println(listaConversacion);
        for (AiChatMemory memoria : listaConversacion) {
            System.out.println("ID: " + memoria.getId() + ", Type: " + memoria.getType() + " , Content: " + memoria.getContent());
        }
    }

    @Test
    void insertar(){
        //Asegurar la existencia de un primer usuario
        Usuario usuarioRelacionado = new Usuario();
        usuarioRelacionado.setId( 1 );

        String id_sesion = "2";
        String contenido = "Otro Mensaje insertado Manualmente";
        AiChatMemory.Type tipo = AiChatMemory.Type.USER;
        AiChatMemory nuevo = new AiChatMemory();

        nuevo.setSessionId(id_sesion);
        nuevo.setContent(contenido);
        nuevo.setType(tipo);
        nuevo.setUsuario(usuarioRelacionado);
        nuevo.setTimestamp(LocalDateTime.now());

        aiChatMemoryService.guardar(nuevo);

    }

    @Test
    void filtradoPorUsuario(){
        List<AiChatMemory> listaConversacion= aiChatMemoryService.findSome(1);
        System.out.println(listaConversacion);
    }


    @Test
    public void fintradoUT(){
        List<AiChatMemory> lista = aiChatMemoryService.filtradoPorUsuarioAndTipo();
        System.out.println(lista);
    }

    //Test que filtra mensajes de usuario
    @Test
    public void fintradoDeUsuario(){
        List<AiChatMemory> lista = aiChatMemoryService.filtrarMensajesUserPorUsuarioId(2);
        System.out.println(lista);
    }
}
