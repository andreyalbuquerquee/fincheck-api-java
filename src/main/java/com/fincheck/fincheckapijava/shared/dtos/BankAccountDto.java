package com.fincheck.fincheckapijava.shared.dtos;

import com.fincheck.fincheckapijava.model.BankAccount;
import com.fincheck.fincheckapijava.model.enums.BankAccountType;

import java.util.UUID;

public record BankAccountDto(
        UUID id,
        String name,
        double initialBalance,
        String color,
        BankAccountType type,
        UUID userId,
        double currentBalance) {
    public BankAccountDto(BankAccount bankAccount, double currentBalance) {
        this(bankAccount.getId(), bankAccount.getName(), bankAccount.getInitialBalance(), bankAccount.getColor(),
                bankAccount.getType(), bankAccount.getUserId(), currentBalance);
    }
}
