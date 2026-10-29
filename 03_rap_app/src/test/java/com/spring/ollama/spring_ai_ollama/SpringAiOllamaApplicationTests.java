package com.spring.ollama.spring_ai_ollama;

import com.spring.ollama.spring_ai_ollama.helper.Helper;
import com.spring.ollama.spring_ai_ollama.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiOllamaApplicationTests {

	@Autowired
	ChatService chatService;
	@Test
	void saveDataToVectorDatabase(){
		System.out.print("Saving data to database");
		this.chatService.saveData(Helper.getData());
		System.out.print("Data is saved Successfully");


	}

}
