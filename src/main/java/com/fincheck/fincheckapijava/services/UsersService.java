package com.fincheck.fincheckapijava.services;

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.view.model.user.LoginResponse;

@Service
public class UsersService {
    @Autowired 
    private UsersRepository usersRepo;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    public User create(User user) {
        user.setId(null);

        if (getByEmail(user.getEmail()).isPresent()) throw new InputMismatchException();

        String hashedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(hashedPassword);

        return usersRepo.save(user);
    }
    
    public List<User> getAll() {
        return usersRepo.findAll();
    }

    public Optional<User> getById(UUID id) {
        return usersRepo.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return usersRepo.findByEmail(email);
    }

    public LoginResponse login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(email, password, Collections.emptyList()));

        SecurityContextHolder.getContext().setAuthentication(auth);

        String token = headerPrefix + jwtService.generateToken(auth).substring(7);

        User user = usersRepo.findByEmail(email).get();

        return new LoginResponse(token, user);
    }

    private static final String headerPrefix = "Bearer";
    
}
