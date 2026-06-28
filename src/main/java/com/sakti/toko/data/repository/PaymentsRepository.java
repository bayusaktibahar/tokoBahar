package com.sakti.toko.data.repository;

import com.sakti.toko.data.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, Long> {

    Optional<Payments> findByParentsOrderNumber(String parentOrderNumber);

}
