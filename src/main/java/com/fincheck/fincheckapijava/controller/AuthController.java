package com.fincheck.fincheckapijava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.services.UsersService;
import com.fincheck.fincheckapijava.shared.dtos.LoginRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UsersService service;
    
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(user));
    }
    
    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@RequestBody LoginRequest login) {
        return ResponseEntity.status(HttpStatus.OK).body(service.login(login.getEmail(), login.getPassword()));
    }
}
