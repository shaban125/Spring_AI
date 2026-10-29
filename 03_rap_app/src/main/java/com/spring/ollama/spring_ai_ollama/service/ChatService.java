package com.spring.ollama.spring_ai_ollama.service;

import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {
    String chatTemplate(String query, String conversationId);

    Flux<String> streamChat(String query, String conversationId);

    void saveData(List<String> List);
}
