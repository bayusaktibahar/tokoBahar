package com.sakti.toko.model.dto;

import com.sakti.toko.data.entity.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentsDTO {
    private Long id;
    private String parentOrderNumber;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private LocalDateTime createdAt;
}
