package com.patika.bloghubservice.dto.response;

import com.patika.bloghubservice.dto.enums.EmailStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EmailErrorResponse(EmailStatus status, String message, LocalDateTime localDateTime, String recipientEmail) {
}
