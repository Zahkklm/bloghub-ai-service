package com.patika.bloghubemailservice.dto.response;

import com.patika.bloghubemailservice.dto.response.enums.EmailStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EmailErrorResponse(EmailStatus status, String message, LocalDateTime localDateTime, String recipientEmail) {
}
