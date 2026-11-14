package com.spring.ollama.spring_ai_ollama.service;

import org.springframework.ai.document.Document;
import java.util.List;

public interface DataLoader {

    List<Document> loadDocumentsFromJson();
    List<Document> loadDocumentsFrompdf();


}
