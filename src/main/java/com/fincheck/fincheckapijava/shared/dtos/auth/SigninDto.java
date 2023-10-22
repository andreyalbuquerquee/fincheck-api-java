package com.fincheck.fincheckapijava.shared.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SigninDto (
    @NotEmpty(message = "email may not be empty!")
    @NotBlank(message = "email may not be empty!")
    @Email(message = "invalid email!")
    String email,
    @NotEmpty(message = "password may not be empty!")
    @Size(min = 8, message = "password should have at least 8 characters!")
    String password
) {}
    
    
    
    
