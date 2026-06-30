package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payments {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "parentOrderNumber", nullable = false)
    private String parentOrderNumber;

    @Column (name = "paymentMethod", nullable = false)
    private String paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private PaymentStatus status;

    @Column (name = "transactionId")
    private String transactionId;

    @Column (name = "createdAt", nullable = false)
    private LocalDateTime createdAt;
}
