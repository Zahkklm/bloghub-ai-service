package com.example.bloghub_ai_service.controller;

import com.example.bloghub_ai_service.service.AIService;
import com.example.bloghub_ai_service.service.VectorStoreService;
import feign.Response;
import io.pinecone.unsigned_indices_model.QueryResponseWithUnsignedIndices;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/ai")
public class AIController {

    private final VectorStoreService vectorStoreService;

    @PostMapping("/store/{userId}")
    public void upsertVectorStore(@PathVariable Long userId,
                                  @RequestParam("blogId") Long blogId) {
        vectorStoreService.upsertVectorStore(userId, blogId);
    }

    @GetMapping("/blogs/{email}")
    public ResponseEntity<QueryResponseWithUnsignedIndices> getVisitedBlogs(@PathVariable String email) {
        return ResponseEntity.ok(vectorStoreService.queryVectoreStore(email));
    }
}
