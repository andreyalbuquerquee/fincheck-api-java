package com.fincheck.fincheckapijava.repository;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fincheck.fincheckapijava.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {
    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId AND date >= :startDate AND date < :endDate",
            nativeQuery = true)
    List<Transaction> findByUserAndDate(@Param("userId") UUID userId, @Param("startDate") Calendar startDate, @Param(
            "endDate") Calendar endDate);

    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId AND date >= :startDate AND date < :endDate AND" +
            " bank_account_id = :bankAccountId", nativeQuery = true)
    List<Transaction> findByUserAndDateAndBankAccount(@Param("userId") UUID userId,
                                                      @Param("startDate") Calendar startDate,
                                                      @Param("endDate") Calendar endDate,
                                                      @Param("bankAccountId") UUID bankAccountId);

    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId AND date >= :startDate AND date < :endDate AND" +
            " type = cast(:type AS transaction_type)", nativeQuery = true)
    List<Transaction> findByUserAndDateAndType(@Param("userId") UUID userId,
                                               @Param("startDate") Calendar startDate,
                                               @Param("endDate") Calendar endDate,
                                               @Param("type") String type);

    @Query(value = "SELECT * FROM transactions WHERE user_id = :userId AND date >= :startDate AND date < :endDate AND" +
            " bank_account_id = :bankAccountId AND type = cast(:type AS transaction_type)", nativeQuery = true)
    List<Transaction> findByUserAndDateAndBankAccountAndType(@Param("userId") UUID userId,
                                                             @Param("startDate") Calendar startDate,
                                                             @Param("endDate") Calendar endDate,
                                                             @Param("bankAccountId") UUID bankAccountId,
                                                             @Param("type") String type);

}
