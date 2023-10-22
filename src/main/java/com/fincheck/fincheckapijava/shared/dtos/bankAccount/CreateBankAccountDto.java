package com.fincheck.fincheckapijava.shared.dtos.bankAccount;

import com.fincheck.fincheckapijava.validation.BankAccountType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateBankAccountDto(
    
    @NotEmpty(message = "name may not be empty!")
    String name,

    @NotEmpty(message = "color may not be empty!")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "color has to be on hexcode format!")
    String color,

    @NotNull(message = "initial balance may not be empty!")
    Double initialBalance,

    @BankAccountType(message = "type must be one of the following values: CHECKING, INVESTMENT, CASH")
    String type
) {
}
