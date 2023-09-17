package com.fincheck.fincheckapijava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fincheck.fincheckapijava.services.BankAccountService;
import com.fincheck.fincheckapijava.shared.dtos.BankAccountDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {
    @Autowired
    BankAccountService bankAccountService;

    
    @PostMapping("/teste")
    public ResponseEntity<Object> teste(@RequestHeader(value = "Authorization") String accessToken, @RequestBody @Valid BankAccountDto createBankAccountDto) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.create(accessToken, createBankAccountDto));
    }
}
