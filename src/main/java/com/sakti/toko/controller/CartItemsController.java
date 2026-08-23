package com.sakti.toko.controller;


import com.sakti.toko.common.annotation.AuthCheck;
import com.sakti.toko.common.annotation.CurrentUser;
import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddCartItemsRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.CartItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cartItems")
@AllArgsConstructor
@Tag(name = "5. Cart", description = "Keranjang belanja pengguna")
public class CartItemsController {
    private final CartItemsService cartItemsService;

    @Operation(summary = "Get all cart items", description = "Menampilkan seluruh item keranjang.")
    @GetMapping("/all")
    public ApiResponse<List<CartItemsDTO>> getAllCartItems(){
        return cartItemsService.getAllCartItems();
    }

    @Operation(summary = "Get cart item by ID", description = "Menampilkan detail satu item keranjang.")
    @GetMapping("/{id}")
    public ApiResponse<CartItemsDTO> getCartItemById(@PathVariable Long id){
        return cartItemsService.getCartItemById(id);
    }

    @Operation(summary = "Add cart item", description = "Menambahkan produk ke keranjang. Butuh login.")
    @PostMapping("/add")
    @AuthCheck
    @SecurityRequirement(name = "bearerAuth")
    public ApiResponse<CartItemsDTO> addCartItem(@RequestBody AddCartItemsRequest addCartItemRequest, @Parameter(hidden = true) @CurrentUser UserDTO currentUser){
        return cartItemsService.addCartItem(addCartItemRequest, currentUser);
    }
}