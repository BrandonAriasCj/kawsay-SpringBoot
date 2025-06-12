package com.kawsay.ia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "ai_chat_memory")
public class AiChatMemory {

    @Id
    @Column(name = "conversation_id", length = 36, nullable = false)
    private String conversationId;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10, nullable = false)
    private Type type;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    public enum Type {
        USER, ASSISTANT, SYSTEM, TOOL
    }

    // Getters y setters
}
