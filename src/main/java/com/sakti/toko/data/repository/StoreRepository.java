package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    <optional> Optional<Store> findByStoreName(String name);

    Optional<Store> findById(UUID user);
}
