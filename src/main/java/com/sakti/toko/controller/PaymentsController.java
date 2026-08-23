package com.sakti.toko.controller;


import com.sakti.toko.model.dto.PaymentsDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdatePaymentStatusRequest;
import com.sakti.toko.service.PaymentsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
@Tag(name = "8. Payments", description = "Status dan riwayat pembayaran")
public class PaymentsController {
    private final PaymentsService paymentsService;

    @Operation(summary = "Get all payments")
    @GetMapping("/all")
    public ApiResponse<List<PaymentsDTO>> getAllPayments(){
        return paymentsService.getAllPayments();
    }

    @Operation(summary = "Get payment by parent order number")
    @GetMapping("/parentOrderNumber")
    public ApiResponse<PaymentsDTO> getAllPaymentsByParentOrderNumber(@RequestParam String parentOrderNumber) {
        return paymentsService.getPaymentsByParentOrderNumber(parentOrderNumber);
    }

    @Operation(summary = "Update payment status")
    @PutMapping("/{parentOrderNumber}/PaymentsStatus")
    public ApiResponse<PaymentsDTO> updatePaymentsStatus(@PathVariable String parentOrderNumber, @RequestBody UpdatePaymentStatusRequest updatePaymentStatusRequest) {
        return paymentsService.updatePaymentsStatus(parentOrderNumber, updatePaymentStatusRequest);
    }
}