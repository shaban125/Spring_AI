package com.spring.ollama.spring_ai_ollama.service;

import reactor.core.publisher.Flux;

import java.util.List;

public interface ChatService {
    String getResponse(String userQuery);
    void saveData(List<String> List);
}
