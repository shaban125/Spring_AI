package com.spring.ollama.spring_ai_ollama;
import com.spring.ollama.spring_ai_ollama.service.DataLoader;
import com.spring.ollama.spring_ai_ollama.service.DataTransformer;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLOutput;
import java.util.List;

@SpringBootTest
class SpringAiOllamaApplicationTests {


	@Autowired
	private DataLoader dataLoader;
	@Autowired
	private DataTransformer dataTransformer;

	@Test
	void loadDocumentsFromJson() {
		var documents = dataLoader.loadDocumentsFromJson();
		System.out.println(documents.size());

		documents.forEach(item -> {
			System.out.println(item);
		});
	}

	@Test
	void loadDocumentsFromPdf() {
		List<Document> documents = dataLoader.loadDocumentsFrompdf();
		System.out.println(documents.size());
		documents.forEach(item -> {
			System.out.println(item);
			System.out.println("------------------------");
		});
		System.out.println("Read Succesfully");


		var transformedDocument = this.dataTransformer.transform(documents);
		System.out.println(transformedDocument.size());
	}

}
