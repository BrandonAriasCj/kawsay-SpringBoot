package com.kawsay.ia.service;
import com.kawsay.ia.repositoty.AiChatMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kawsay.ia.entity.AiChatMemory;

import java.util.List;


@Service
public class AiChatMemoryService {
    @Autowired
    private AiChatMemoryRepository aiChatMemoryRepository;

    public List<AiChatMemory> findAllService() {
        return aiChatMemoryRepository.findAll();
    }
}
