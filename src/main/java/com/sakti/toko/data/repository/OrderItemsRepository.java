package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.OrderItems;
import com.sakti.toko.data.entity.Orders;
import com.sakti.toko.data.entity.Product;
import com.sakti.toko.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long>  {
    List<OrderItems> findByOrder(Orders order);

    boolean existsByOrderUserAndProduct(User user, Product product);
}
