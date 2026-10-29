package com.spring.ollama.spring_ai_ollama.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
//import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;



@Configuration
public class AiConfig {



    @Bean
    public ChatMemory chatMemory() {
        InMemoryChatMemoryRepository inMemoryChatMemoryRepository =new InMemoryChatMemoryRepository();
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryRepository(inMemoryChatMemoryRepository)
                .build();
    }

    private Logger logger = LoggerFactory.getLogger(AiConfig.class);

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {

        this.logger.info("repository ImplementationClass: " + chatMemory.getClass().getName());

//        // Step 1: repository ko ChatMemory mein wrap karo
//        ChatMemory chatMemory = MessageWindowChatMemory.builder()
//                .chatMemoryRepository(chatMemory)
//                .maxMessages(20)
//                .build();

        // Step 2: advisor ko repository nahi, chatMemory do
        MessageChatMemoryAdvisor messageChatMemoryAdvisor =
                MessageChatMemoryAdvisor.builder(chatMemory).build();
        return builder
                .defaultAdvisors(messageChatMemoryAdvisor,new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("games")))
//               .defaultSystem("You are a helpful coding assistant. You are an expert in coding.")
                .defaultOptions(OllamaChatOptions.builder()
                .model("codellama:latest")
                        .temperature(0.2)
                        .numPredict(100))
                .build();
    }
}