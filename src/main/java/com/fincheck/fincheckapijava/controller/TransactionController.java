package com.fincheck.fincheckapijava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fincheck.fincheckapijava.services.TransactionService;
import com.fincheck.fincheckapijava.shared.dtos.TransactionDto;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(value = "Authorization") String accessToken, @RequestBody TransactionDto createTransactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(accessToken, createTransactionDto));
    }
}
