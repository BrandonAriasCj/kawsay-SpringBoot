package com.kawsay.ia;
import com.kawsay.ia.entity.AiChatMemory;
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

    @Test
    void findAll() {
        //assertNotNull(aiChatMemoryService);
        List<AiChatMemory> listaConversacion= aiChatMemoryService.findAllService();
        System.out.println(listaConversacion);
        for (AiChatMemory memoria : listaConversacion) {
            System.out.println("ID: " + memoria.getConversationId() + ", Type: " + memoria.getType() + " , Content: " + memoria.getContent());
        }
    }

    @Test
    void insertar(){

        String id_conversación = "2";
        String contenido = "Otro Mensaje insertado Manualmente";
        AiChatMemory.Type tipo = AiChatMemory.Type.USER;
        AiChatMemory nuevo = new AiChatMemory();

        nuevo.setConversationId(id_conversación);
        nuevo.setContent(contenido);
        nuevo.setType(tipo);
        nuevo.setTimestamp(LocalDateTime.now());

        aiChatMemoryService.guardar(nuevo);



    }
}
