package com.sakti.toko.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {
    private String sku; // Stock Keeping Unit (Kode Unik Produk)
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
}
