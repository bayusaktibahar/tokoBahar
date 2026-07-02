package com.sakti.toko.service;


import com.sakti.toko.data.entity.OrderStatus;
import com.sakti.toko.data.entity.Orders;
import com.sakti.toko.data.entity.Store;
import com.sakti.toko.data.entity.User;
import com.sakti.toko.data.repository.OrderRepository;
import com.sakti.toko.data.repository.StoreRepository;
import com.sakti.toko.data.repository.UserRepository;
import com.sakti.toko.model.dto.OrdersDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateOrderStatusRequest;
import com.sakti.toko.service.details.OrdersDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrdersService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final OrdersDetailService ordersDetailService;

    public ApiResponse<List<OrdersDTO>> getAllOrdersByUser(UUID userId) {
        var targetUser = userRepository.findById(userId);

        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "User Not Found",
                    null
            );
        }

        var user = targetUser.get();

        List <Orders> orders = orderRepository.findByUser(user);

        if (orders.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Orders Not Found",
                    null
            );
        }

        var result = ordersDetailService.getOrdersList(orders);

        return new ApiResponse<>(
                true,
                200,
                "Orders Found",
                result
        );

    }

    public ApiResponse<OrdersDTO> getOrdersById(Long ordersId){

        var order =  orderRepository.findById(ordersId).orElseThrow();

        var result = ordersDetailService.getOrdersDetails(order);

        return new ApiResponse<>(
                true,
                201,
                "Data Orders Found",
                result
        );
    }


    public ApiResponse<List<OrdersDTO>> getOrdersByStoreId(Long storeId){
        var targetStore = storeRepository.findById(storeId);

        if (targetStore.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Store Not Found",
                    null
            );
        }

        var store = targetStore.get();

        List<Orders> orders = orderRepository.findByStore(store);
        if (orders.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Orders Not Found",
                    null
            );
        }

        var  result = ordersDetailService.getOrdersList(orders);

        return new ApiResponse<>(
                true,
                201,
                "Data Store Found",
                result
        );
    }

    @Transactional
    public ApiResponse<OrdersDTO> updateOrderStatus(Long orderId, UpdateOrderStatusRequest updateOrderStatusRequest){

        var targetOrder = orderRepository.findById(orderId);

        if (targetOrder.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    400,
                    "Order Not Found",
                    null
            );
        }

        var order = targetOrder.get();

        order.setOrderStatus(updateOrderStatusRequest.getOrderStatus());

        orderRepository.save(order);

        var result = ordersDetailService.getOrdersDetails(order);
        return new ApiResponse<>(
                true,
                200,
                "Order Status Updated",
                result
        );

    }



}
