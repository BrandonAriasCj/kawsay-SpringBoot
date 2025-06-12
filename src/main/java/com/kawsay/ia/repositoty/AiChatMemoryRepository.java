package com.kawsay.ia.repositoty;
import com.kawsay.ia.entity.AiChatMemory;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AiChatMemoryRepository extends JpaRepository<AiChatMemory, String> {
}
