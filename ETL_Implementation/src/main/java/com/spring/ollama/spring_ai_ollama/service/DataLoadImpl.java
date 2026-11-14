package com.spring.ollama.spring_ai_ollama.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
public class DataLoadImpl implements DataLoader{
    @Value("classpath:sample_data.json")
    private Resource jsonresource;
    @Value("classpath:Cricket-Rules.pdf")
    private Resource pdfresource;

    @Override
    public List<Document> loadDocumentsFromJson() {

        System.out.print("Starting Loading Json");


        var jsonReader = new JsonReader(jsonresource, "project");
        var listDocuments = jsonReader.read();
        return listDocuments;
    }

    @Override
    public List<Document> loadDocumentsFrompdf() {

        var pdfReader = new PagePdfDocumentReader(pdfresource,
                PdfDocumentReaderConfig
                .builder()
                .withPageBottomMargin(0)
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
                .withNumberOfTopTextLinesToDelete(0)
                .build())
                .withPagesPerDocument(1)
                .build());

        return pdfReader.read();
    }
}
