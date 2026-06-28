package com.sakti.toko.controller;


import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.CartItemsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartItems")
@AllArgsConstructor
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @GetMapping("/all")
    public ApiResponse<List<CartItemsDTO>> getAllCartItems(){
        return cartItemsService.getAllCartItems();
    }

    @GetMapping("/{id}")
    public ApiResponse<CartItemsDTO> getCartItemById(@PathVariable Long id){
        return cartItemsService.getCartItemById(id);
    }
}
