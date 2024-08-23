package com.example.bloghub_ai_service.client;

import com.example.bloghub_ai_service.dto.BlogResponse;
import com.example.bloghub_ai_service.dto.response.GenericResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bloghub-service", path = "/api/v1/blogs")
public interface BlogServiceClient {

    @GetMapping("{blogId}/users/{userId}")
    ResponseEntity<GenericResponse<BlogResponse>> findBlogById(@PathVariable(name = "blogId") Long blogId, @PathVariable(name = "userId") Long userId);
}
