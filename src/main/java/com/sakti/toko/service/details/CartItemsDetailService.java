package com.sakti.toko.service.details;


import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.data.repository.ProductRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartItemsDetailService {
    private final ProductDetailService productDetailService;
    private final UserDetailService userDetailService;

    public CartItemsDTO getCartItemsDTO(CartItems cartItems) {

        UserDTO userDTO = userDetailService.getUserDetailWithoutSession(cartItems.getUser());

        ProductDTO productDTO = productDetailService.getProductDetailWithoutReview(cartItems.getProduct());

        return CartItemsDTO.builder()
                .id(cartItems.getId())
                .userDTO(userDTO)
                .productDTO(productDTO)
                .quantity(cartItems.getQuantity())
                .build();
    }



}
