package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.Store;
import com.sakti.toko.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    <optional> Optional<Store> findByStoreName(String name);

    <optional> Optional<Store> findByUser(User user);
}
