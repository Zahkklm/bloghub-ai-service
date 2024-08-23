package com.example.bloghub_ai_service.service.impl;

import com.example.bloghub_ai_service.service.AIService;
import com.example.bloghub_ai_service.service.VectorStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AIServiceImpl implements AIService {

    private final OllamaChatModel ollamaChatModel;
    private final VectorStoreService vectorStoreService;

    public List<String> queryOllama(List<String> blogTitles, String query) {



        return new ArrayList<>();
    }
}
