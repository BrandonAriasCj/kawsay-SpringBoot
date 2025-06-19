package com.kawsay.ia.repository;
import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiChatMemoryRepository extends JpaRepository<AiChatMemory, Long> {
    List<AiChatMemory> findByUsuario(Usuario usuario);
    List<AiChatMemory> findByUsuarioAndType(Usuario usuario, AiChatMemory.Type tipo);
    List<AiChatMemory> findBySessionIdOrderByTimestampAsc(String sessionId);
}
