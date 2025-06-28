package com.kawsay.ia.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/historias")
public class HistoriasController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:prompts/historias.ai.st")
    private Resource stPromptTemplate;

    public HistoriasController(ChatClient.Builder chatBuilder, VectorStore vectorStore) {
        this.chatClient = chatBuilder
                .defaultAdvisors(new PromptChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
        this.vectorStore = vectorStore;
    }

    // Metodo post para realizar la consulta
    @PostMapping("/chat")
    public String generateResponse(@RequestBody PsicologoRequest request) {
        PromptTemplate promptTemplate = new PromptTemplate(stPromptTemplate);

        var promptParameters = new HashMap<String, Object>();
        promptParameters.put("pregunta", request.getPregunta());
        promptParameters.put("intereses", String.join(", ", request.getIntereses()));

        var prompt = promptTemplate.create(promptParameters);
        var response = this.chatClient.prompt(prompt).call().chatResponse();

        return response.getResult().getOutput().getText();
    }

    // DTO para recibir los datos del usuario
    public static class PsicologoRequest {
        private String pregunta;
        private List<String> intereses;

        public String getPregunta() {
            return pregunta;
        }

        public void setPregunta(String pregunta) {
            this.pregunta = pregunta;
        }

        public List<String> getIntereses() {
            return intereses;
        }

        public void setIntereses(List<String> intereses) {
            this.intereses = intereses;
        }
    }
}
