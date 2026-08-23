package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.CheckoutRequest;
import com.sakti.toko.service.CheckoutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
@Tag(name = "6. Checkout", description = "Proses checkout multi-store")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @Operation(summary = "Checkout", description = "Memproses checkout dari isi keranjang, bisa mencakup lebih dari satu toko sekaligus.")
    @PostMapping
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<String> checkout(@RequestBody CheckoutRequest checkoutRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser) {
        return checkoutService.checkout(checkoutRequest, currentUser);
    }
}