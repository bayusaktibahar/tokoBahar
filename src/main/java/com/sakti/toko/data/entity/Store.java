package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // Relasi 1-to-1: Satu user Seller cuma bisa punya satu toko
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "store_name", unique = true, nullable = false)
    private String storeName;
}
