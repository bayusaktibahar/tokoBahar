package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailIgnoreCase(String email);
    Optional<User> findById(Boolean isSuspended);

    @Modifying
    @Query(value = "DELETE FROM products WHERE store_id IN (SELECT id FROM stores WHERE user_id = :userId)", nativeQuery = true)
    void deleteProductsByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "DELETE FROM stores WHERE user_id = :userId", nativeQuery = true) // <--- Jika nama kolom di DB Anda bukan user_id (misal owner_id), silakan sesuaikan
    void deleteStoresByUserId(@Param("userId") UUID userId);

    @Modifying
    @Query(value = "DELETE FROM reviews WHERE user_id = :userId", nativeQuery = true)
    void deleteReviewsByUserId(@Param("userId") UUID userId);
}

;

