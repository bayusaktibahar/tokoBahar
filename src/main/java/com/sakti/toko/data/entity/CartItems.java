package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cartitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}
