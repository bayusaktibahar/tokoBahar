package com.sakti.toko.controller;


import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.CheckoutRequest;
import com.sakti.toko.service.CheckoutService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ApiResponse<String> checkout(@RequestBody CheckoutRequest checkoutRequest) {
        return checkoutService.checkout(checkoutRequest);
    }

}
