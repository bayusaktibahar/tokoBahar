package com.sakti.toko.model.dto;

import com.sakti.toko.data.entity.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private Long id;
    private String parentOrderNumber;
    private String orderNumber;
    private UserDTO userDTO;
    private StoreDTO storeDTO;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}
