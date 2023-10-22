package com.fincheck.fincheckapijava.shared.dtos.transaction;

import java.time.LocalDateTime;

import com.fincheck.fincheckapijava.validation.IsUuid;
import com.fincheck.fincheckapijava.validation.IsTransactionType;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionDto(
    @NotEmpty(message = "bank account id may not be empty!")
    @IsUuid
    String bankAccountId,

    @NotEmpty(message = "category id may not be empty!")
    @IsUuid
    String categoryId,

    @NotEmpty(message = "name may not be empty!")
    String name,

    @NotNull(message = "value may not be null!")
    @Positive(message = "value has to be a positive number!")
    double value,

    @NotNull(message = "date may not be null!")
    LocalDateTime date,

    @IsTransactionType()
    String type
) { }
