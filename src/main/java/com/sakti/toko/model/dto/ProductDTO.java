package com.sakti.toko.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private StoreDTO storeDTO;
    private String sku; // Stock Keeping Unit (Kode Unik Produk)
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private List<ReviewsDTO> reviews;
}
