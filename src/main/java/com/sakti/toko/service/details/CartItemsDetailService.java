package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.model.dto.CartItemsDTO;
import com.sakti.toko.model.dto.ProductDTO;
import com.sakti.toko.model.dto.UserDTO;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartItemsDetailService {

    private final ProductDetailService productDetailService;
    private final UserDetailService userDetailService;

    public CartItemsDTO getCartItemsDetails(CartItems cartItems) {

        UserDTO userDTO = userDetailService.getUserDetailWithoutSession(cartItems.getUser());

        ProductDTO productDTO = productDetailService.getProductDetailWithoutReview(cartItems.getProduct());

        return CartItemsDTO.builder()
                .id(cartItems.getId())
                .userDTO(userDTO)
                .productDTO(productDTO)
                .quantity(cartItems.getQuantity())
                .build();
    }

    public List<CartItemsDTO> getCartItemsList(List<CartItems> cartItems) {
        return cartItems.stream()
                .map(this::getCartItemsDetails)
                .collect(Collectors.toList());
    }

}
