package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "parentOrderNumber", nullable = false)
    private String parentOrderNumber;

    @Column (name = "OrderNumber",unique = true,  nullable = false)
    private String orderNumber;

    @ManyToOne
    @JoinColumn (name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn (name = "store_id", nullable = false)
    private Store store;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus orderStatus;

    @Column (name = "totalPrice", nullable = false)
    private BigDecimal totalPrice;

    @Column (name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

}
