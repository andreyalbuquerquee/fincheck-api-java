package com.fincheck.fincheckapijava.controller;

import java.util.UUID;

import com.fincheck.fincheckapijava.shared.dtos.bankAccount.UpdateBankAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fincheck.fincheckapijava.services.BankAccountService;
import com.fincheck.fincheckapijava.shared.dtos.bankAccount.CreateBankAccountDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bank-accounts")
public class BankAccountController {
    @Autowired
    BankAccountService service;

    @PostMapping()
    public ResponseEntity<Object> create(@RequestHeader(value = "Authorization") String accessToken, @RequestBody @Valid CreateBankAccountDto createBankAccountDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(accessToken, createBankAccountDto));
    }

    @GetMapping()
    public ResponseEntity<Object> findAll(@RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByUserId(accessToken));
    }

    @PutMapping("/{bankAccountId}")
    public ResponseEntity<Object> update(@RequestHeader(value = "Authorization") String accessToken,
                                         @PathVariable UUID bankAccountId,
                                         @RequestBody @Valid UpdateBankAccountDto updateBankAccountDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(accessToken, bankAccountId,
                updateBankAccountDto));
    }

    @DeleteMapping("/{bankAccountId}")
    public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization") String accessToken, @PathVariable UUID bankAccountId) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(service.delete(accessToken, bankAccountId));
    }
    
}
