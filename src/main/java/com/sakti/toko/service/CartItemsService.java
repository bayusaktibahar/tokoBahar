package com.sakti.toko.service;


import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.data.repository.CartItemsRepository;
import com.sakti.toko.data.repository.ProductRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.request.AddCartItemsRequest;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.service.details.CartItemsDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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

    @Transactional
    public ApiResponse<CartItemsDTO> addCartItem(AddCartItemsRequest addCartItemsRequest, UserDTO currentUser){
        var targetProduct = productRepository.findById(addCartItemsRequest.getProductId());

        if(targetProduct.isEmpty()){
            return new ApiResponse<>(
                    false,
                    400,
                    "Product not found",
                    null
            );
        }

        var product = targetProduct.get();

        if (product.getStock() < 1){
            return new ApiResponse<>(
                    false,
                    403,
                    "Product Out Of Stock",
                    null
            );
        }

        var targetUser = userRepository.findById(currentUser.getId());

        if (targetUser.isEmpty()){
            return new ApiResponse<>(
                    false,
                    403,
                    "User not found",
                    null
            );
        }

        var user = targetUser.get();

        if (user.getIsSuspended()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Your account has been suspended",
                    null
            );
        }

        var cartItem = new CartItems();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(addCartItemsRequest.getQuantity());

        cartItemsRepository.save(cartItem);
        var result = cartItemsDetailService.getCartItemsDetails(cartItem);

        return new ApiResponse<>(
                true,
                200,
                "Product Success Add to Cart",
                result
        );
    }




}
