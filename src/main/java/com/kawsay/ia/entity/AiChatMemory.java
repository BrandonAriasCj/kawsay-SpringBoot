package com.kawsay.ia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import lombok.*;


@Data
@Entity
@NoArgsConstructor
@Table(name = "ai_chat_memory")
public class AiChatMemory {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne
    @Column(name = "sesion", nullable = false)
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "usuario", nullable = false)
    private Usuario usuario;

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

    @Builder
    public AiChatMemory(String sessionId, Usuario usuario, String content, Type type, LocalDateTime timestamp) {
        this.sessionId = sessionId;
        this.usuario = usuario;
        this.content = content;
        this.type = type;
        this.timestamp = timestamp;
    }
}
