package com.fincheck.fincheckapijava.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fincheck.fincheckapijava.model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(Authentication auth) {
        int weekInMiliseconds = 604800000;
        
        Date expirationDate = new Date(new Date().getTime() + weekInMiliseconds);

        User user = (User) auth.getPrincipal();
        
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
