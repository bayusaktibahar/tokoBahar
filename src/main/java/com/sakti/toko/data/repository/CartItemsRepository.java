package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.CartItems;
import com.sakti.toko.data.entity.Product;
import com.sakti.toko.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {

    List<CartItems> findByUser(User user);

    List<CartItems> findByUserAndProduct(User user, Product product);

}
