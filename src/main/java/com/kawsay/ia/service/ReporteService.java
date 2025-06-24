package com.kawsay.ia.service;

import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Reporte;
import com.kawsay.ia.repository.ReporteRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import com.kawsay.ia.config.AuthUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
    @Autowired
    private AuthUtils authUtils;



    //Crud basico de reportes
    public List<Reporte> findAllReporteService(){
        return reporteRepository.findAll();
    }
    public Reporte insertarReporte(Reporte reporte){
        return reporteRepository.save(reporte);
    }
    public Reporte actualizarReporte(Reporte reporte){
        return reporteRepository.save(reporte);
    }


    //Operaciones
    @Autowired
    @Lazy
    AiChatMemoryService aiChatMemoryService;

    @Autowired
    ChatClient chatClient;
    public String generarReporteUsuario(int userId){

        List<AiChatMemory> elementos = aiChatMemoryService.find10UltimosElementos(userId);


        String memoriaCorta = "Historial de conversacion:";
        for (AiChatMemory elemento : elementos) {
            String message = elemento.getContent();
            String tipo = elemento.getType().toString();
            String time = elemento.getTimestamp().toString();
            String conjunto = "Rol: " + tipo + ", Mensaje: " + message + ", Time: " + time;

            memoriaCorta = memoriaCorta + "\n" + conjunto;
        }

        String input = "Eres un psicologo especialista en analisis de comportamienta y tu labor será generar un resumen psicologico con emogis e interactivo sobre el estado psicologico de de comversacion del usuario, el resumen es dirigido para el especilista psicologico que trata al usuaio no para el usuario";
        String respuesta = chatClient
                .prompt(input)
                .system(memoriaCorta)
                .call()
                .content();

        System.out.println(respuesta);
        return respuesta;

    }

}
