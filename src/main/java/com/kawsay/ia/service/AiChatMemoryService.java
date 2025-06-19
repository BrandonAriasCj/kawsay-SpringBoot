package com.kawsay.ia.service;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.mapper.AiChatMemoryMapper;
import com.kawsay.ia.repository.AiChatMemoryRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kawsay.ia.entity.AiChatMemory;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class AiChatMemoryService {
    @Autowired
    private AiChatMemoryRepository aiChatMemoryRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ChatClient chatClient;


    public List<AiChatMemory> findAllService() {
        return aiChatMemoryRepository.findAll();
    }

    public List<AiChatMemory> findSome(Integer id) {
        Usuario u = new Usuario();
        u.setId(id);
        return aiChatMemoryRepository.findByUsuario(u);
    }

    public void guardar(AiChatMemory aiChatMemory) {
        aiChatMemoryRepository.save(aiChatMemory);
    }

    public List<AiChatMemory> findBySessionIdOrdered(String sessionId) {
        return aiChatMemoryRepository.findBySessionIdOrderByTimestampAsc(sessionId);
    }

    public void guardarMensaje(String sessionId, AiChatMemory.Type role, String mensaje) {
        AiChatMemory m = new AiChatMemory();
        m.setSessionId(sessionId);
        m.setType(role);
        m.setContent(mensaje);
        m.setTimestamp(LocalDateTime.now());
        aiChatMemoryRepository.save(m);
    }
    public String enviarMensajeYObtenerRespuesta(String sessionId, String mensajeUsuario, ChatClient chatClient) {
        guardar(AiChatMemoryMapper.toAiChatMemory(sessionId, AiChatMemory.Type.USER, mensajeUsuario));

        List<AiChatMemory> historial = findBySessionIdOrdered(sessionId);
        List<Message> mensajes = AiChatMemoryMapper.toChatMessages(historial);

        String respuesta = chatClient
                .prompt("Hola")
                .call()
                .content();

        guardar(AiChatMemoryMapper.toAiChatMemory(sessionId, AiChatMemory.Type.ASSISTANT, respuesta));

        return respuesta;
    }

}
