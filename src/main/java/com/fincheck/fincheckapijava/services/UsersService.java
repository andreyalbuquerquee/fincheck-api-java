package com.fincheck.fincheckapijava.services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.UsersRepository;

@Service
public class UsersService {
    @Autowired 
    private UsersRepository usersRepo;

    @Autowired 
    private PasswordEncoder passwordEncoder;
    
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
    
}
