package com.kawsay.ia.controllers;

import com.kawsay.ia.service.AiChatMemoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/api/users")
public class MultiUserJdbcChatMemoryController {

    private final ChatClient chatClient;

    private final ChatMemory chatMemory;
    @Autowired
    private AiChatMemoryService chatMemoryService;

    public MultiUserJdbcChatMemoryController(ChatClient chatClient, ChatMemory chatMemory) {
        this.chatClient = chatClient;
        this.chatMemory = chatMemory;
    }

    @PostMapping("/{userId}/chat")
    public String chat(
            @PathVariable String userId,
            @RequestBody String request) {

        return chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, userId))
                .user(request)
                .call()
                .content();
    }

    @GetMapping("/{userId}/history")
    public List<Message> getHistory(@PathVariable String userId) {
        return chatMemory.get(userId, 100);
    }

    @DeleteMapping("/{userId}/history")
    public String clearHistory(@PathVariable String userId) {
        chatMemory.clear(userId);

        return "Conversation history cleared for user: " + userId;
    }

    @PostMapping("/stream")
    public String chat() {

        return chatClient
                .prompt()
                .user("Que es stream en spring ai")
                .call()
                .content();
    }
}