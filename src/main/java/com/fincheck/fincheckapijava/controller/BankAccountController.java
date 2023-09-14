package com.fincheck.fincheckapijava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fincheck.fincheckapijava.shared.dtos.CreateBankAccountDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {
    @PostMapping("/teste")
    public ResponseEntity<Object> teste(@RequestBody @Valid CreateBankAccountDto createBankAccountDto) {
        return ResponseEntity.status(HttpStatus.OK).body("passou");
    }
}
