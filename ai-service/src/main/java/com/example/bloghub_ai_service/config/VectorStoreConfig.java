package com.example.bloghub_ai_service.config;

import io.pinecone.clients.Index;
import io.pinecone.clients.Pinecone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VectorStoreConfig {

    @Bean
    public Pinecone pinecone() {
        return new Pinecone.Builder(System.getenv("PINECONE_API_KEY")).build();
    }

    @Bean
    public Index index() {
        return pinecone().getIndexConnection(System.getenv("PINECONE_INDEX_NAME"));
    }
}
