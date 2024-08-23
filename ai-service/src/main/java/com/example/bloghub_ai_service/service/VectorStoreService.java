package com.example.bloghub_ai_service.service;

import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;

public interface VectorStoreService {

    void upsertVectorStore(Long userId, Long blogId);
    QueryResponseWithUnsignedIndices queryVectoreStore(String email);
}
