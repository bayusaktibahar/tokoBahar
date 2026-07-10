package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.model.dto.UserDTO;
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
    @AuthCheck
    public ApiResponse<String> checkout(@RequestBody CheckoutRequest checkoutRequest, @CurrentUser UserDTO currentUser) {
        return checkoutService.checkout(checkoutRequest, currentUser);
    }

}
