package com.sakti.toko.service.details;

import com.sakti.toko.data.entity.Orders;
import com.sakti.toko.model.dto.OrdersDTO;
import com.sakti.toko.model.dto.OrderItemsDTO;
import com.sakti.toko.model.dto.UserDTO;
import com.sakti.toko.model.dto.StoreDTO;
import com.sakti.toko.data.repository.OrderItemsRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrdersDetailService {

    private final UserDetailService userDetailService;
    private final StoreDetailService storeDetailService;
    private final OrderItemsDetailService orderItemsDetailService;
    private final OrderItemsRepository orderItemsRepository;

    public OrdersDTO getOrdersDetails(Orders orders){

        UserDTO userDTO = userDetailService.getUserDetailWithoutSession(orders.getUser());

        StoreDTO storeDTO = storeDetailService.getStoreDetails(orders.getStore());

        // ✅ Ambil dari repository, langsung konversi pakai DetailService
        List<OrderItemsDTO> orderItems = orderItemsDetailService
                .getOrdersItemsList(orderItemsRepository.findByOrder(orders));

        return OrdersDTO.builder()
                .id(orders.getId())
                .parentOrderNumber(orders.getParentOrderNumber())
                .orderNumber(orders.getOrderNumber())
                .userDTO(userDTO)
                .storeDTO(storeDTO)
                .orderStatus(orders.getOrderStatus())
                .totalPrice(orders.getTotalPrice())
                .createdAt(orders.getCreatedAt())
                .orderItems(orderItems)
                .build();

    }

    public List<OrdersDTO> getOrdersList(List<Orders> orderList){
        return orderList.stream()
                .map(this::getOrdersDetails)
                .collect(Collectors.toList());
    }



}
