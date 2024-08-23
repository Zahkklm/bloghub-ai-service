package com.example.bloghub_ai_service.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

    @Bean
    public OllamaApi ollamaApi() {
        return new OllamaApi();
    }

    @Bean
    public EmbeddingModel embeddingModel(OllamaApi ollamaApi) {
        return new OllamaEmbeddingModel(ollamaApi);
    }

    @Bean
    public OllamaOptions ollamaOptions() {
        return new OllamaOptions()
                .withModel("llama3")
                .withTopK(2)
                .withTemperature(0.4F);
    }

    @Bean
    public OllamaChatModel chatModel(OllamaApi ollamaApi, OllamaOptions ollamaOptions) {
        return new OllamaChatModel(ollamaApi, ollamaOptions);
    }









}
