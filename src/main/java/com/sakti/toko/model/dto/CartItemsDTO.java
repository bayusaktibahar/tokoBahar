package com.sakti.toko.model.dto;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemsDTO {
    private Long id;
    private UserDTO userDTO;
    private ProductDTO productDTO;
    private Integer quantity;
}
