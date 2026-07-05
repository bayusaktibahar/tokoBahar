package com.sakti.toko.service;


import com.sakti.toko.data.entity.Payments;
import com.sakti.toko.data.repository.PaymentsRepository;
import com.sakti.toko.model.dto.PaymentsDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdatePaymentStatusRequest;
import com.sakti.toko.service.details.PaymentsDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class PaymentsService {
    private final PaymentsRepository paymentsRepository;
    private final PaymentsDetailService paymentsDetailService;

    public ApiResponse<List<PaymentsDTO>> getAllPayments() {
        List<Payments> payments = paymentsRepository.findAll();

        var result = paymentsDetailService.getPaymentsList(payments);

        return new ApiResponse<>(
                true,
                200,
                "Data All Payments Found",
                result
        );
    }

    public ApiResponse<PaymentsDTO> getPaymentsByParentOrderNumber(String parentOrderNumber){
        var payments = paymentsRepository.findByParentOrderNumber(parentOrderNumber).orElseThrow();

        var result = paymentsDetailService.getPaymentsDetails(payments);

                return new ApiResponse<>(
                        true,
                        200,
                        "Payments Found",
                        result
                );
    }

    @Transactional
    public ApiResponse<PaymentsDTO> updatePaymentsStatus(String parentOrderNumber, UpdatePaymentStatusRequest updatePaymentStatusRequest ){
        var targetPayments = paymentsRepository.findByParentOrderNumber(parentOrderNumber);

        if(targetPayments.isEmpty()){
            return new ApiResponse<>(
                    false,
                    400,
                    "Payment Not Found",
                    null
            );
        }

        var payments = targetPayments.get();

        payments.setPaymentStatus(updatePaymentStatusRequest.getPaymentStatus());

        paymentsRepository.save(payments);

        var result = paymentsDetailService.getPaymentsDetails(payments);
        return new ApiResponse<>(
                true,
                200,
                "Payment Updated",
                result
        );

    }





}
