package com.fincheck.fincheckapijava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fincheck.fincheckapijava.services.UsersService;
import com.fincheck.fincheckapijava.shared.dtos.SigninDto;
import com.fincheck.fincheckapijava.shared.dtos.SignupDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UsersService service;
    
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody @Valid SignupDto signupDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(signupDto));
    }
    
    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody @Valid SigninDto signinDto) {
        return ResponseEntity.status(HttpStatus.OK).body(service.signin(signinDto));
    }
}
