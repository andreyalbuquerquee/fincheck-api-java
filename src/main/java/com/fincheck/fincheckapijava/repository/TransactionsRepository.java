package com.fincheck.fincheckapijava.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fincheck.fincheckapijava.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {
    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId AND date >= :startDate AND date < :endDate", nativeQuery = true)
    List<Transaction> findByUserAndDate(@Param("userId") UUID userId, @Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);
}
