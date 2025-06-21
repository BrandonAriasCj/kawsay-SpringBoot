package com.kawsay.ia.controllers;

import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Usuario;
import com.kawsay.ia.repository.AiChatMemoryRepository;
import com.kawsay.ia.repository.UsuarioRepository;
import com.kawsay.ia.service.AiChatMemoryService;
import org.apache.catalina.User;
import org.springframework.ai.chat.client.ChatClient;
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

    @GetMapping("/{userId}/mensajes10/")
    public List<AiChatMemory> chat3asdfdgs(@PathVariable Integer userId) {
        return aiChatMemoryService.find10UltimosElementos(userId);
    }

    /*
        Insersion de datos
    */
    @Autowired
    private AiChatMemoryService servicioDeInsertar;
    @PostMapping("/{userId}/mensaje/user/")
    public AiChatMemory insesrtarMensajeUser(@PathVariable Integer userId, @RequestBody AiChatMemory mensaje) {
        return servicioDeInsertar.insesrtarMensajeUserService(userId, mensaje);
    }

    @PostMapping("/{userId}/mensaje/assistant/")
    public AiChatMemory insesrtarMensajeAssistant(@PathVariable Integer userId, @RequestBody AiChatMemory mensaje) {
        return servicioDeInsertar.insesrtarMensajeAssistantService(userId, mensaje);
    }


    /*
       Procesar datos
     */
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private AiChatMemoryService serviceIAChatMemory;

    @PostMapping("/request/{userId}/mensaje/")
    public List<AiChatMemory> request(@PathVariable Integer userId, @RequestBody String mensaje) {
        String input = mensaje;

        List<AiChatMemory> elementos = aiChatMemoryService.find10UltimosElementos(userId);


        String memoriaCorta = "Historial de conversacion:";
        for (AiChatMemory elemento : elementos) {
            String message = elemento.getContent();
            String tipo = elemento.getType().toString();
            String time = elemento.getTimestamp().toString();
            String conjunto = "Rol: " + tipo + ", Mensaje: " + message + ", Time: " + time;

            memoriaCorta = memoriaCorta + "\n" + conjunto;
        }


        String respuesta = chatClient
                .prompt(input)
                .system(memoriaCorta)
                .call()
                .content();

        AiChatMemory newUserMensaje = new AiChatMemory();
        newUserMensaje.setContent(input);
        AiChatMemory newAssistantMensaje = new AiChatMemory();
        newAssistantMensaje.setContent(respuesta);

        //insesrtar mensaje
        serviceIAChatMemory.insesrtarMensajeUserService(userId, newUserMensaje);
        //Insertar respuesta
        serviceIAChatMemory.insesrtarMensajeAssistantService(userId, newAssistantMensaje);

        System.out.println(memoriaCorta);
        return serviceIAChatMemory.find10UltimosElementos(userId);
    }


}
