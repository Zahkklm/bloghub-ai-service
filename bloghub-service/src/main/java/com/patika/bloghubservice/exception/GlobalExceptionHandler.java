package com.patika.bloghubservice.exception;

import com.patika.bloghubservice.dto.enums.EmailStatus;
import com.patika.bloghubservice.dto.enums.PaymentStatus;
import com.patika.bloghubservice.dto.response.EmailErrorResponse;
import com.patika.bloghubservice.dto.response.GenericResponse;
import com.patika.bloghubservice.dto.response.PaymentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BlogHubException.class)
    public GenericResponse<String> handleBlogHubException(BlogHubException blogHubException){
        return GenericResponse.error(blogHubException.getMessage(), "Blog oluşturulamadı");
    }

    @ExceptionHandler(UserStatusNotChangedException.class)
    public GenericResponse<String> handleUserStatusNotChangedException(UserStatusNotChangedException ex){
        return GenericResponse.error(ex.getMessage());
    }

    @ExceptionHandler(EmailNotDeliveredException.class)
    public ResponseEntity<GenericResponse<EmailErrorResponse>> handleEmailNotDeliveredException(EmailNotDeliveredException ex) {
        EmailErrorResponse emailErrorResponse = EmailErrorResponse.builder()
                .message(ex.getMessage())
                .status(EmailStatus.NOT_DELIVERED)
                .localDateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.internalServerError().body(GenericResponse.error(emailErrorResponse, ex.getMessage()));
    }

    @ExceptionHandler(PaymentServiceException.class)
    public ResponseEntity<GenericResponse<PaymentResponse>> handlePaymentServiceException(PaymentServiceException ex) {
        PaymentResponse paymentResponse = PaymentResponse.builder()
                .paymentStatus(PaymentStatus.NOT_PAID)
                .createdDateTime(LocalDateTime.now())
                .build();

        return ResponseEntity.internalServerError()
                .body(GenericResponse.error(paymentResponse, ex.getMessage()));
    }

}
