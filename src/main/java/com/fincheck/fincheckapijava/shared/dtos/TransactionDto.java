package com.fincheck.fincheckapijava.shared.dtos;

import java.sql.Timestamp;

import com.fincheck.fincheckapijava.validation.ValidateTransactionType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record TransactionDto(
    @NotEmpty(message = "bank account id may not be empty!")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Inform a valid UUID")
    String bankAccountId,

    @NotEmpty(message = "category id may not be empty!")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "Inform a valid UUID")
    String categoryId,

    @NotEmpty(message = "Name may not be empty!")
    String name,

    @NotNull(message = "Value may not be null!")
    @Positive(message = "Value has to be a positive number!")
    double value,

    @NotNull(message = "Date may not be null!")
    Timestamp date,

    @ValidateTransactionType(message = "type must be one of the following values: INCOME, EXPENSE")
    String type
) { }
