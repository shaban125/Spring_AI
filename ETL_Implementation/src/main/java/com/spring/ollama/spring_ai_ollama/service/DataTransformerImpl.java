package com.spring.ollama.spring_ai_ollama.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataTransformerImpl implements DataTransformer{

    @Override
    public List<Document> transform(List<Document> documents) {
        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(800)
                .build();
        return splitter.transform(documents);

    }
}
