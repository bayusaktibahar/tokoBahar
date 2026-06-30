package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.OrderItems;
import com.sakti.toko.model.dto.OrdersItemsDTO;
import com.sakti.toko.model.dto.ProductDTO;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderItemsDetailService {
    private final ProductDetailService productDetailService;

    public OrdersItemsDTO getOrdersItemsDetails(OrderItems orderItems) {

        ProductDTO productDTO = productDetailService.getProductDetailWithoutReview(orderItems.getProduct());

        return OrdersItemsDTO.builder()
                .id(orderItems.getId())
                .productDTO(productDTO)
                .quantity(orderItems.getQuantity())
                .priceAtPurchase(orderItems.getPriceAtPurchase())
                .build();

    }

    public List<OrdersItemsDTO> getOrdersItemsList(List<OrderItems> orderItemsList) {
        return orderItemsList.stream()
                .map(this::getOrdersItemsDetails)
                .collect(Collectors.toList());
    }
}
