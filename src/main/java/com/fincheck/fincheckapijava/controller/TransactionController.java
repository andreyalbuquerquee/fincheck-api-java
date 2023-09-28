package com.fincheck.fincheckapijava.controller;

import java.util.UUID;

import com.fincheck.fincheckapijava.model.enums.TransactionType;
import com.fincheck.fincheckapijava.validation.IsTransactionType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fincheck.fincheckapijava.services.TransactionService;
import com.fincheck.fincheckapijava.shared.dtos.TransactionDto;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader(value = "Authorization") String accessToken,
                                         @RequestBody @Valid TransactionDto createTransactionDto) {
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
            @RequestParam(required = false) @IsTransactionType TransactionType type
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findAllByUserId(
                accessToken,
                month,
                year,
                bankAccountId,
                type));
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<Object> update(
            @RequestHeader(value = "Authorization") String accessToken,
            @PathVariable UUID transactionId,
            @RequestBody @Valid TransactionDto updateTransactionDto
    ) {
    return ResponseEntity.status(HttpStatus.OK).body(transactionService.update(accessToken, transactionId,
            updateTransactionDto));
    }
}
