package com.sakti.toko.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 1, 2, 3...
    private Long id;

    // Relasi Many-to-One: Banyak produk bisa dimiliki oleh satu Toko (Store)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(unique = true, nullable = false, length = 50)
    private String sku; // Stock Keeping Unit (Kode Unik Produk)

    @Column(nullable = false, length = 150)
    private String name;

    @Column(columnDefinition = "TEXT") // Mengubah tipe data di DB menjadi TEXT (bukan varchar biasa)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2) // Contoh: maks 9.999.999.999,99
    private BigDecimal price;

    @Column(nullable = false)
    private Integer stock;
}
