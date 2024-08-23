package com.patika.bloghubservice.exception;

import java.math.BigDecimal;

public class PaymentServiceException extends RuntimeException{
    public PaymentServiceException(String message) {
        super(message);
    }

    public PaymentServiceException(BigDecimal amount, String message) {
        super(message);
    }
}
