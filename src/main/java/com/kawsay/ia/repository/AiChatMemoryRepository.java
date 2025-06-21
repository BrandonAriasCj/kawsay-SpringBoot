package com.kawsay.ia.repository;
import com.kawsay.ia.entity.AiChatMemory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AiChatMemoryRepository extends JpaRepository<AiChatMemory, Long> {
    List<AiChatMemory> findBySessionIdOrderByTimestampAsc(String sessionId);
}
