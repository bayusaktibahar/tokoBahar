package com.sakti.toko.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddCartItemsRequest {
    private UUID id;
    private long productId;
    private Integer quantity;
}
