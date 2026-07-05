package com.sakti.toko.model.request;

import com.sakti.toko.data.entity.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentStatusRequest {
    private PaymentStatus paymentStatus;
}
