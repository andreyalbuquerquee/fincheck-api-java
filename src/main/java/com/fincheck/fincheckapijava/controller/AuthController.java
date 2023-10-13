package com.fincheck.fincheckapijava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fincheck.fincheckapijava.services.AuthService;
import com.fincheck.fincheckapijava.shared.dtos.auth.SigninDto;
import com.fincheck.fincheckapijava.shared.dtos.auth.SignupDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthService service;
    
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupDto signupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(signupDto));
    }
    
    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody @Valid SigninDto signinDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.signin(signinDto));
    }

    @GetMapping("/teste")
    public ResponseEntity<Object> teste(@RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCurrentUserId(accessToken));
    }
}
