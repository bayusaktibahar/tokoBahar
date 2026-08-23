package com.sakti.toko.controller;

import com.sakti.toko.model.dto.OrdersDTO;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.UpdateOrderStatusRequest;
import com.sakti.toko.service.OrdersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@Tag(name = "7. Orders", description = "Riwayat dan status pesanan")
public class OrderController {
    private final OrdersService ordersService;

    @Operation(summary = "Get all orders by user")
    @GetMapping("/all")
    public ApiResponse<List<OrdersDTO>> getAllOrdersByUser(@RequestParam UUID name){
        return ordersService.getAllOrdersByUser(name);
    }

    @Operation(summary = "Get orders by store")
    @GetMapping("/store/{storeId}")
    public ApiResponse<List<OrdersDTO>> getOrdersByStore(@PathVariable Long storeId){
        return ordersService.getOrdersByStoreId(storeId);
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public ApiResponse<OrdersDTO> getOrders(@PathVariable Long id) {
        return ordersService.getOrdersById(id);
    }

    @Operation(summary = "Update order status")
    @PutMapping("/{orderId}/statusOrder")
    public ApiResponse<OrdersDTO> updateOrderStatus(@PathVariable Long orderId, @RequestBody UpdateOrderStatusRequest updateOrderStatusRequest){
        return ordersService.updateOrderStatus(orderId, updateOrderStatusRequest);
    }
}