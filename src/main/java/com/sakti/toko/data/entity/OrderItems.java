package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orderitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column (name = "quantity",  nullable = false)
    private Integer quantity;

    @Column (name = "priceAtPurchase", nullable = false)
    private BigDecimal priceAtPurchase;

}
