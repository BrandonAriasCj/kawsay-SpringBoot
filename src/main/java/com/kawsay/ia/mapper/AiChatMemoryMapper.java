package com.kawsay.ia.mapper;

import com.kawsay.ia.entity.AiChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AiChatMemoryMapper {

    /**
     * Convierte una lista de entidades AiChatMemory a mensajes ChatMessage que entiende Spring AI.
     */
    public static List<Message> toChatMessages(List<AiChatMemory> historial) {
        List<Message> mensajes = new ArrayList<>();

        for (AiChatMemory m : historial) {
            if ("USER".equalsIgnoreCase(m.getType().toString())) {
                mensajes.add(new UserMessage(m.getContent()));
            } else if ("SYSTEM".equalsIgnoreCase(m.getType().toString())) {
                mensajes.add(new AssistantMessage(m.getContent()));
            }
            // Si hay más roles como "system", podrías agregarlos aquí.
        }

        return mensajes;
    }

    /**
     * Crea una nueva entidad AiChatMemory desde un mensaje (usuario o IA).
     */
    public static AiChatMemory toAiChatMemory(String sessionId, AiChatMemory.Type role, String mensaje) {
        AiChatMemory memory = new AiChatMemory();
        memory.setSessionId(sessionId);
        memory.setType(role);
        memory.setContent(mensaje);
        memory.setTimestamp(LocalDateTime.now());
        return memory;
    }
}
