package com.fincheck.fincheckapijava.shared.dtos;

import com.fincheck.fincheckapijava.validation.ValidateBankAccountType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateBankAccountDto(
    
    @NotEmpty(message = "Name may not be empty!")
    @Pattern(regexp = "^[a-zA-Z\s]*$", message = "Name has to be only text")
    String name,

    @NotEmpty(message = "Color may not be empty!")
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color has to be on hexcode format!")
    String color,

    @NotNull(message = "Initial balance may not be empty!")
    Double initialBalance,

    @ValidateBankAccountType(message = "type must be one of the following values: CHECKING, INVESTMENT, CASH")
    String type
) {
    
}