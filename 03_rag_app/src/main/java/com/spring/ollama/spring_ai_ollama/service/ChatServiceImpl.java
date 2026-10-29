package com.spring.ollama.spring_ai_ollama.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("classpath:prompt/system-message.st")
    private Resource systemMessage;
    @Value("classpath:prompt/user-message.st")
    private Resource userMessage;
    private VectorStore vectorStore;

    private ChatClient chatClient;

    public ChatServiceImpl(ChatClient chatClient, VectorStore vectorStore) {   // Builder nahi, ChatClient
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;

        // builder.build() nahi
    }

    ;

//    @Autowired
//    public ChatServiceImpl(ChatClient.Builder builder) {
//        this.chatClient = builder.build();
//    }

    @Override
    public String chatTemplate(String query, String userId) {

//        SearchRequest searchRequest = SearchRequest.builder().
//                topK(3)
//                .similarityThreshold(0.6)
//                .query(query).build();
//        List<Document> documents = this.vectorStore.similaritySearch(searchRequest);
//        if (documents.isEmpty()) {
//            return "This query is not in my database.";
//        }
//        List<String> documentList = documents.stream().map(Document::getText).toList();
//        String contextData = String.join(",", documentList);
//        logger.info("Context Data: {}", contextData);
        return this.chatClient
                .prompt()
//                .system(system -> system.text(this.systemMessage).param("Documents", contextData))
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .user(user -> user.text(this.userMessage).param("query", query))
                .call()
                .content();
    }

    @Override
    public Flux<String> streamChat(String query, String conversationId) {
        return this.chatClient
                .prompt()
                .system(system -> system.text(this.systemMessage))
                .user(query)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .stream()
                .content();
    }

    @Override
    public void saveData(List<String> list) {
        List<Document> documentList = list.stream().map(Document::new).toList();
        this.vectorStore.add(documentList);
    }


}
