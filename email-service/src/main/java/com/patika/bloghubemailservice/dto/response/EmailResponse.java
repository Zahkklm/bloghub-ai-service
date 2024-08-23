package com.patika.bloghubemailservice.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record EmailResponse(String emailId, String recipientEmail, LocalDateTime localDateTime) {
}
