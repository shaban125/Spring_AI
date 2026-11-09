package com.spring.ollama.spring_ai_ollama.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.generation.augmentation.ContextualQueryAugmenter;
import org.springframework.ai.rag.preretrieval.query.expansion.MultiQueryExpander;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.join.ConcatenationDocumentJoiner;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

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

    }

    @Override
    public void saveData(List<String> list) {
        List<Document> documentList = list.stream().map(Document::new).toList();
        this.vectorStore.add(documentList);
    }

    @Override

    public String getResponse(String userQuery){

        var advisors = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(
                        RewriteQueryTransformer.builder()
                                .chatClientBuilder(chatClient.mutate().clone())
                                .build()
                )
                .queryExpander(MultiQueryExpander.builder().chatClientBuilder(chatClient.mutate().clone()).build())
                .documentRetriever(
                        VectorStoreDocumentRetriever.builder()
                                .vectorStore(vectorStore)
                                .topK(3)
                                .similarityThreshold(0.3)
                                .build()
                )
                .documentJoiner(new ConcatenationDocumentJoiner())
                .queryAugmenter(ContextualQueryAugmenter.builder().build())

                .build();
    return this.chatClient
            .prompt()
            .advisors(advisors)
            .user(userQuery)
            .call()
            .content();
    }


}
