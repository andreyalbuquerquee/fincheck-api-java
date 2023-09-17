package com.fincheck.fincheckapijava.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    
    @PostMapping()
    public ResponseEntity<Object> create(@RequestHeader(value = "Authorization") String accessToken, @RequestBody @Valid BankAccountDto createBankAccountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankAccountService.create(accessToken, createBankAccountDto));
    }

    @PutMapping("/{bankAccountId}")
    public ResponseEntity<Object> update(@RequestHeader(value = "Authorization") String accessToken, @PathVariable UUID bankAccountId, @RequestBody @Valid BankAccountDto updateBankAccountDto) {
        return ResponseEntity.status(HttpStatus.OK).body(bankAccountService.update(accessToken, bankAccountId, updateBankAccountDto));
    }

    @DeleteMapping("/{bankAccountId}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization") String accessToken, @PathVariable UUID bankAccountId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(bankAccountService.delete(accessToken, bankAccountId));
    }
    
}
