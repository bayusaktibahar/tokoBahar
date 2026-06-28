package com.sakti.toko.model.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StoreDTO {
    private Long id;
    private UserDTO userDTO;
    private String storeName;
}
