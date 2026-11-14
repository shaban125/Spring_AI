package com.Docker.DMR;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Scanner;

@SpringBootTest
class DmrApplicationTests {
	@Autowired
	private ChatClient chatClient;
	@Test
	void testChatclien() {

		System.out.println("Started............");
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your query............");
		var query = "hi";

		var response = this.chatClient.prompt().user(query).call().content();
		System.out.println(response);
	}

}
