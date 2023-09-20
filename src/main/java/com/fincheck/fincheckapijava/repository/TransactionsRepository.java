package com.fincheck.fincheckapijava.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fincheck.fincheckapijava.model.Transaction;

public interface TransactionsRepository extends JpaRepository<Transaction, UUID> {
    
}
