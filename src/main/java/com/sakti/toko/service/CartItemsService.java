package com.sakti.toko.service;


import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.data.repository.CartItemsRepository;
import com.sakti.toko.data.repository.ProductRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.details.CartItemsDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartItemsService {
    private final CartItemsRepository cartItemsRepository;
    private final CartItemsDetailService cartItemsDetailService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ApiResponse<List<CartItemsDTO>> getAllCartItems(){
        List<CartItems> cartItems = cartItemsRepository.findAll();

        var result = cartItemsDetailService.getCartItemsList(cartItems);

        return new ApiResponse<>(
                true,
                200,
                "Success Creating List of Cart Items",
                result
        );

    }

    public ApiResponse<CartItemsDTO> getCartItemById(Long cartItemsId){
        var cartItems = cartItemsRepository.findById(cartItemsId).orElseThrow();

        var result = cartItemsDetailService.getCartItemsDetails(cartItems);

        return new ApiResponse<>(
                true,
                200,
                "Success Get Cart Items Details",
                result
        );
    }

}
