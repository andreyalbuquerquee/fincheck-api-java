package com.fincheck.fincheckapijava.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fincheck.fincheckapijava.model.BankAccount;
import com.fincheck.fincheckapijava.model.User;

import java.util.Optional;


public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByIdAndUser(UUID id, User user);
}
