package com.sakti.toko.service.details;

import com.sakti.toko.model.dto.PaymentsDTO;
import com.sakti.toko.data.entity.Payments;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaymentsDetailService {

    public PaymentsDTO getPaymentsDetails(Payments  payments) {

        return PaymentsDTO.builder()
                .id(payments.getId())
                .parentOrderNumber(payments.getParentOrderNumber())
                .paymentMethod(payments.getPaymentMethod())
                .status(payments.getStatus())
                .transactionId(payments.getTransactionId())
                .createdAt(payments.getCreatedAt())
                .build();

    }

    public List<PaymentsDTO> getPaymentsList(List<Payments> paymentsList) {
        return paymentsList.stream()
                .map(this::getPaymentsDetails)
                .collect(Collectors.toList());
    }
}
