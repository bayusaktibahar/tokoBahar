package com.sakti.toko.controller;

import com.sakti.toko.model.dto.OrdersDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateOrderStatusRequest;
import com.sakti.toko.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {
    private final OrdersService ordersService;

    @GetMapping("/all")
    public ApiResponse<List<OrdersDTO>> getAllOrdersByUser(@RequestParam UUID name){
        return ordersService.getAllOrdersByUser(name);
    }

    @GetMapping("/store/{storeId}")
    public ApiResponse<List<OrdersDTO>> getOrdersByStore(@PathVariable Long storeId){
        return  ordersService.getOrdersByStoreId(storeId);
    }

    @GetMapping("/{id}")
    public ApiResponse<OrdersDTO> getOrders(@PathVariable Long id) {
        return ordersService.getOrdersById(id);
    }

    @PutMapping("/{orderId}/statusOrder")
    public ApiResponse<OrdersDTO> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest){
        return ordersService.updateOrderStatus(orderId, updateOrderStatusRequest);
    }
}
