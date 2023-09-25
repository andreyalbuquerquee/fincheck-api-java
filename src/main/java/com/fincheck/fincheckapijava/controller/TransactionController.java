package com.fincheck.fincheckapijava.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fincheck.fincheckapijava.model.enums.TransactionType;
import com.fincheck.fincheckapijava.services.TransactionService;
import com.fincheck.fincheckapijava.shared.dtos.TransactionDto;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(value = "Authorization") String accessToken,
                                         @RequestBody TransactionDto createTransactionDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.create(
                accessToken,
                createTransactionDto));
    }

    @GetMapping
    public ResponseEntity<Object> findAll(
            @RequestHeader(value = "Authorization") String accessToken,
            @RequestParam int month,
            @RequestParam int year,
            @RequestParam(required = false) UUID bankAccountId,
            @RequestParam(required = false) TransactionType type
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllByUserId(
                accessToken,
                month,
                year,
                bankAccountId,
                type));
    }
}
