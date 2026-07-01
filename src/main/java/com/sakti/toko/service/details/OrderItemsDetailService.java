package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.OrderItems;
import com.sakti.toko.model.dto.OrderItemsDTO;
import com.sakti.toko.model.dto.ProductDTO;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemsDetailService {

    private final ProductDetailService productDetailService;

    public OrderItemsDTO getOrderItemsDetails(OrderItems orderItems) {

        ProductDTO productDTO = productDetailService.getProductDetailWithoutReview(orderItems.getProduct());

        return OrderItemsDTO.builder()
                .id(orderItems.getId())
                .productDTO(productDTO)
                .quantity(orderItems.getQuantity())
                .priceAtPurchase(orderItems.getPriceAtPurchase())
                .build();

    }

    public List<OrderItemsDTO> getOrdersItemsList(List<OrderItems> orderItemsList) {
        return orderItemsList.stream()
                .map(this::getOrderItemsDetails)
                .collect(Collectors.toList());
    }
}
