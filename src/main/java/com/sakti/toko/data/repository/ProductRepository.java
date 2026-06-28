package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.Product; // Mengambil entity Product dari folder dto
import com.sakti.toko.data.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Mencari produk berdasarkan kodenya (SKU)
    Optional<Product> findBySku(String sku);

    Optional<Product> findByName(String name);

    // Mencari semua produk yang dimiliki oleh Toko tertentu (berdasarkan storeId)
    List<Product> findByStore(Store store);
}