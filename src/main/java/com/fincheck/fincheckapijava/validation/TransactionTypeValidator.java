package com.fincheck.fincheckapijava.validation;

import java.util.Arrays;
import java.util.List;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TransactionTypeValidator implements ConstraintValidator<ValidateTransactionType ,String> {

    @Override
    public boolean isValid(String transactionType, ConstraintValidatorContext context) {
        
        List<String> transactionTypes = Arrays.asList("INCOME", "EXPENSE");
        return transactionTypes.contains(transactionType);
    }
    
}
