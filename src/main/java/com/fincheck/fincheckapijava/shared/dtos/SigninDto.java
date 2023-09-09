package com.fincheck.fincheckapijava.shared.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record SigninDto (
    @NotEmpty(message = "Email não deve estar vazio!")
    @NotBlank(message = "Email não deve estar vazio!")
    @Email(message = "Informe um e-mail válido!")
    String email,
    @NotEmpty(message = "Senha não pode estar vazia!")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    String password
) {}
    
    
    
    
