package com.fincheck.fincheckapijava.controller;

import com.fincheck.fincheckapijava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService service;

    @GetMapping("/me")
    public ResponseEntity<Object> me(@RequestHeader(value = "Authorization") String accessToken) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUserById(accessToken));
    }
}
