package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddCartItemsRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.CartItemsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping("/add")
    @AuthCheck
    public  ApiResponse<CartItemsDTO> addCartItem(@RequestBody AddCartItemsRequest addCartItemRequest, @CurrentUser UserDTO currentUser){
        return cartItemsService.addCartItem(addCartItemRequest, currentUser );
    }

}
