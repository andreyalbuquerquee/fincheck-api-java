package com.fincheck.fincheckapijava.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> unauthorizedException(UnauthorizedException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> authenticationException(AuthenticationException ex) {
        ExceptionResponse response = new ExceptionResponse("Credenciais inválidas!", "Unauthorized", HttpStatus.UNAUTHORIZED.value());
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> conflictException(ConflictException ex) {
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), "Conflict", HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

}
