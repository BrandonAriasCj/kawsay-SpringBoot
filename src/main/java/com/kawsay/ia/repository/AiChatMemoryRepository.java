package com.kawsay.ia.repository;
import com.kawsay.ia.entity.AiChatMemory;
import com.kawsay.ia.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiChatMemoryRepository extends JpaRepository<AiChatMemory, Long> {
    //Conversación general
    List<AiChatMemory> findByUsuario(Usuario usuario);
    List<AiChatMemory> findByUsuarioOrderByTimestampAsc(Usuario usuario);
    List<AiChatMemory> findByUsuarioOrderByTimestampDesc(Usuario usuario);
    List<AiChatMemory> findTop10ByUsuarioOrderByTimestampDesc(Usuario usuario);

    //Solu mensajes user tipo user
    List<AiChatMemory> findByUsuarioAndType(Usuario usuario, AiChatMemory.Type tipo);
    List<AiChatMemory> findByUsuarioAndTypeOrderByTimestampAsc(Usuario usuario, AiChatMemory.Type tipo);
    List<AiChatMemory> findByUsuarioAndTypeOrderByTimestampDesc(Usuario usuario, AiChatMemory.Type tipo);
    List<AiChatMemory> findByType(AiChatMemory.Type type);
    List<AiChatMemory> findBySessionIdOrderByTimestampAsc(String sessionId);
}
