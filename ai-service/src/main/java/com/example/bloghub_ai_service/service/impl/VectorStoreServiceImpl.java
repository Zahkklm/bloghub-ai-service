package com.example.bloghub_ai_service.service.impl;

import com.example.bloghub_ai_service.client.BlogServiceClient;
import com.example.bloghub_ai_service.service.VectorStoreService;
import com.google.protobuf.Struct;
import io.pinecone.clients.Index;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import io.pinecone.unsigned_indices_model.ScoredVectorWithUnsignedIndices;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.ai.ollama.OllamaEmbeddingModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VectorStoreServiceImpl implements VectorStoreService {

    private final OllamaEmbeddingModel ollamaEmbeddingModel;
    private final Index index;
    private final BlogServiceClient blogServiceClient;

    @Override
    public void upsertVectorStore(Long userId, Long blogId) {

        var blog = blogServiceClient.findBlogById(blogId, userId);



       /* List<Double> doubleEmbedding = ollamaEmbeddingModel.embed(blogTitle);

        List<Float> floatEmbedding = doubleEmbedding.stream()
                .map(Double::floatValue)
                .toList();


        index.upsert(String.valueOf(UUID.randomUUID()), floatEmbedding, null, null, null, email);*/
    }

    @Override
    public QueryResponseWithUnsignedIndices queryVectoreStore(String email) {
        var listItem = index.list(email, 50).getVectorsList();

        QueryResponseWithUnsignedIndices indices = index.queryByVectorId(5, listItem.getFirst().getId(), email, true, false);

        List<List<Float>> viewedVectors = indices.getMatchesList().stream().map(ScoredVectorWithUnsignedIndices::getValuesList).toList();
        List<Float> concatedViewedVectors = viewedVectors.stream()
                .flatMap(Collection::stream)
                .toList();

        QueryResponseWithUnsignedIndices recommendatedVectors = index.query(50, concatedViewedVectors, null, null, null, null, null, true, false);

        var result = index.queryByVectorId(50, email, null, null, true, true);


        return indices;
    }
}
