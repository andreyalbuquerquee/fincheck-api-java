package com.fincheck.fincheckapijava.security;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.fincheck.fincheckapijava.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTService {
    
    @Value("${api.security.token.secret}")
    private String jwtSecret;

    public String generateToken(Authentication auth) {
        int weekInMiliseconds = 604800000;
        
        Date expirationDate = new Date(new Date().getTime() + weekInMiliseconds);

        User user = (User) auth.getPrincipal();
        
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Optional<UUID> activeUserId(String token) {
        try {
            Claims claims = parse(token).getBody();
            return Optional.ofNullable(UUID.fromString(claims.getSubject()));
        } catch (Exception e) {
           return Optional.empty(); 
        }
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
    }
}
