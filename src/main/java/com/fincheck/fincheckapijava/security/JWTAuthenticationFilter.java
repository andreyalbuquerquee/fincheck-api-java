package com.fincheck.fincheckapijava.security;

import java.io.IOException;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    
    // Before every endpoint request, this method is used
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestToken = getToken(request);

        Optional<UUID> userId = jwtService.activeUserId(requestToken);

        if (!userId.isPresent()) throw new InputMismatchException("Token inválido.");

        UserDetails user = customUserDetailsService.loadById(userId.get());

        // Verify if the user is authenticated, here we could verify the permissions too.
        UsernamePasswordAuthenticationToken authentication = 
        new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        // Changing the authentication 
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Setting the authentication for the spring security context 
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
    }


    private String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        //Verify if the token isn't empty
        if (!StringUtils.hasText(token)) return null;

        //Ignores the "Bearer" before the token
        return token.substring(7);
    }
    
}
