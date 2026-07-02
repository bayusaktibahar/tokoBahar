package com.sakti.toko.service;

import com.sakti.toko.data.entity.*;
import com.sakti.toko.data.repository.*;
import com.sakti.toko.model.request.ApiResponse;
import com.sakti.toko.model.request.CheckoutRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckoutService {

    private final CartItemsRepository cartItemsRepository;
    private final OrderRepository orderRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final PaymentsRepository paymentsRepository;
    private final UserRepository userRepository;

    @Transactional
    public ApiResponse<String> checkout(CheckoutRequest checkoutRequest) {

        var targetUser = userRepository.findById(checkoutRequest.getUser());

        if (targetUser.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "User Not Found",
                    null
            );
        }

        var user = targetUser.get();

        List <CartItems> cartItems = cartItemsRepository.findByUser(user);

        if (cartItems.isEmpty()) {
            return new ApiResponse<>(
                    false,
                    403,
                    "Item In Your Cart Is Empty",
                    null
            );
        }

        String parentOrderNumber = "PAY-" + UUID.randomUUID().toString().substring(0, 8);

        Map<Store, List<CartItems>> groupedByStore = cartItems.stream()
                .collect(Collectors.groupingBy(
                        cartItem -> cartItem.getProduct().getStore()
                ));

        BigDecimal totalPayment = BigDecimal.ZERO;

        for (Map.Entry<Store, List<CartItems>> entry : groupedByStore.entrySet()) {
            Store store = entry.getKey();
            List<CartItems> storeCartItems = entry.getValue();

            BigDecimal storeTotalPrice = BigDecimal.ZERO;
            for (CartItems item : storeCartItems) {
                BigDecimal itemTotal = item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity()));
                storeTotalPrice = storeTotalPrice.add(itemTotal);
            }

            totalPayment = totalPayment.add(storeTotalPrice);

            String orderNumber = "ORD-" + store.getStoreName().toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();

            Orders order = Orders.builder()
                    .parentOrderNumber(parentOrderNumber)
                    .orderNumber(orderNumber)
                    .user(user)
                    .store(store)
                    .orderStatus(OrderStatus.PENDING)
                    .totalPrice(storeTotalPrice)
                    .createdAt(LocalDateTime.now())
                    .build();

            orderRepository.save(order);

            for (CartItems item : storeCartItems) {
                OrderItems orderItems = OrderItems.builder()
                        .order(order)
                        .product(item.getProduct())
                        .quantity(item.getQuantity())
                        .priceAtPurchase(item.getProduct().getPrice())
                        .build();

                orderItemsRepository.save(orderItems);
            }
        }

        Payments payments = Payments.builder()
                .parentOrderNumber(parentOrderNumber)
                .paymentMethod(checkoutRequest.getPaymentMethod())
                .paymentStatus(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        paymentsRepository.save(payments);

        cartItemsRepository.deleteAll(cartItems);

        return new ApiResponse<>(
                true,
                201,
                "Checkout Successful",
                parentOrderNumber
        );
    }

}
