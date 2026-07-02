package com.sakti.toko.model.request;

import com.sakti.toko.data.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderStatusRequest {
    private OrderStatus orderStatus;
}