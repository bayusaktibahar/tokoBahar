package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.Orders;
import com.sakti.toko.data.entity.Store;
import com.sakti.toko.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(User user);

    List<Orders> findByStore(Store store);

    List<Orders> findByParentOrderNumber(String parentOrderNumber);
}
