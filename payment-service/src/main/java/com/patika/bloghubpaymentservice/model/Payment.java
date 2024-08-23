package com.patika.bloghubpaymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private LocalDateTime createdDateTime;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private Long userId;

    public Payment(BigDecimal amount, LocalDateTime createdDateTime, PaymentStatus paymentStatus, Long userId) {
        this.amount = amount;
        this.createdDateTime = createdDateTime;
        this.paymentStatus = paymentStatus;
        this.userId = userId;
    }
}
