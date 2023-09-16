package com.fincheck.fincheckapijava.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fincheck.fincheckapijava.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    
}
