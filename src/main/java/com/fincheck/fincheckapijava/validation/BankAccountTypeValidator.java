package com.fincheck.fincheckapijava.validation;

import java.util.Arrays;
import java.util.List;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class BankAccountTypeValidator implements ConstraintValidator<ValidateBankAccountType, String> {

    @Override
    public boolean isValid(String bankAccountType, ConstraintValidatorContext context) {
        
        List<String> bankAccountTypes = Arrays.asList("CHECKING", "INVESTMENT", "CASH");
        return bankAccountTypes.contains(bankAccountType);
    }

    
}
