package com.sakti.toko.model.dto;


import lombok.*;

import java.math.BigDecimal;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersItemsDTO {
    private Long id;
    private ProductDTO productDTO;
    private Integer quantity;
    private BigDecimal priceAtPurchase;
}
