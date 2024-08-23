package com.example.bloghub_ai_service.dto;

import com.example.bloghub_ai_service.dto.enums.BlogStatus;
import com.example.bloghub_ai_service.dto.response.BlogCommentResponse;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record BlogResponse(
        String title,
        String text,
        LocalDateTime createdDateTime,
        BlogStatus blogStatus,
        Long likeCount,
        List<BlogCommentResponse> blogCommentList) {
}
