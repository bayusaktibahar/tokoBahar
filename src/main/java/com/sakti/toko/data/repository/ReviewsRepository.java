package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.Product;
import com.sakti.toko.data.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    Optional<Reviews> findByUser_Id(UUID id);

    List<Reviews> findByProduct(Product product);

    List<Reviews> findByProduct_Id(Long productId);
}
