package com.kawsay.ia.controllers;

import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.AiChatMemoryRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.service.AiChatMemoryService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ChatController {
    @Autowired
    AiChatMemoryService aiChatMemoryService;
    @Autowired
    private AiChatMemoryRepository aiChatMemoryRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    /*
        Lectura de datos
     */
    @GetMapping("/{userId}/mensajes")
    public List<AiChatMemory> chat(@PathVariable Integer userId) {
        return aiChatMemoryService.filtrarMensajesPorUsuarioIdOrdenadoAsc(userId);
    }

    @GetMapping("/{userId}/mensajes/asc")
    public List<AiChatMemory> chat2(@PathVariable Integer userId) {
        return aiChatMemoryService.filtrarMensajesPorUsuarioIdOrdenadoAsc(userId);
    }

    @GetMapping("/{userId}/mensajes/desc")
    public List<AiChatMemory> chat3(@PathVariable Integer userId) {
        return aiChatMemoryService.filtrarMensajesPorUsuarioIdOrdenadoDesc(userId);
    }

    @GetMapping("/{userId}/mensajes/own")
    public List<AiChatMemory> chatasdf(@PathVariable Integer userId) {
        return aiChatMemoryService.filtrarMensajesUserPorUsuarioId(userId);
    }

    @GetMapping("/{userId}/mensajes/asc/own")
    public List<AiChatMemory> chat2asdf(@PathVariable Integer userId) {
        return aiChatMemoryService.filtrarMensajesUserPorUsuarioIdOrdenadoAsc(userId);
    }

    @GetMapping("/{userId}/mensajes/desc/own")
    public List<AiChatMemory> chat3asdf(@PathVariable Integer userId) {
        return aiChatMemoryService.filtrarMensajesUserPorUsuarioIdOrdenadoDesc(userId);
    }


    /*
        Insersion de datos
    */
    @PostMapping("/{userId}/mensaje/user/")
    public AiChatMemory insesrtarMensajeUser(@PathVariable Integer userId, @RequestBody AiChatMemory mensaje) {
        Usuario usuario = usuarioRepository.getUsuariosById(userId);
        mensaje.setSessionId("1asdf"+userId);
        mensaje.setTimestamp(LocalDateTime.now());
        mensaje.setType(AiChatMemory.Type.USER);
        mensaje.setUsuario(usuario);
        return aiChatMemoryRepository.save(mensaje);
    }

    @PostMapping("/{userId}/mensaje/assistant/")
    public AiChatMemory insesrtarMensajeAssistant(@PathVariable Integer userId, @RequestBody AiChatMemory mensaje) {
        Usuario usuario = usuarioRepository.getUsuariosById(userId);
        mensaje.setSessionId("1asdf"+userId);
        mensaje.setTimestamp(LocalDateTime.now());
        mensaje.setType(AiChatMemory.Type.ASSISTANT);
        mensaje.setUsuario(usuario);
        return aiChatMemoryRepository.save(mensaje);
    }


    /*
       Procesar datos
     */
}
