package com.sakti.toko.controller;


import com.sakti.toko.model.dto.PaymentsDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdatePaymentStatusRequest;
import com.sakti.toko.service.PaymentsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentsController {
    private final PaymentsService paymentsService;

    @GetMapping("/all")
    public ApiResponse<List<PaymentsDTO>> getAllPayments(){
        return paymentsService.getAllPayments();
    }

    @GetMapping("/parentOrderNumber")
    public ApiResponse<PaymentsDTO> getAllPaymentsByParentOrderNumber(@RequestParam String parentOrderNumber) {
        return paymentsService.getPaymentsByParentOrderNumber(parentOrderNumber);
    }

    @PutMapping("/{parentOrderNumber}/PaymentsStatus")
    public ApiResponse<PaymentsDTO> updatePaymentsStatus(@PathVariable String parentOrderNumber, @RequestBody UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        return paymentsService.updatePaymentsStatus(parentOrderNumber, updatePaymentStatusRequest);
    }

}
