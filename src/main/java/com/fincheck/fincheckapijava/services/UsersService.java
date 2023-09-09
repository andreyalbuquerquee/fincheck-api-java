package com.fincheck.fincheckapijava.services;

import java.util.ArrayList;
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

import com.fincheck.fincheckapijava.model.Category;
import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.model.enums.TransactionType;
import com.fincheck.fincheckapijava.repository.CategoriesRepository;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.view.model.user.LoginResponse;

@Service
public class UsersService {
    @Autowired 
    private UsersRepository usersRepo;

    @Autowired 
    private CategoriesRepository categoriesRepo;
    
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

        user = usersRepo.save(user);
        
        List<Category> categories = new ArrayList<>();

        categories.add(new Category("Salário", "travel", TransactionType.INCOME, user));
        categories.add(new Category("Freelance", "freelance", TransactionType.INCOME, user));
        categories.add(new Category("Outro", "other", TransactionType.INCOME, user));

        categories.add(new Category("Casa", "home", TransactionType.EXPENSE, user));
        categories.add(new Category("Alimentação", "food", TransactionType.EXPENSE, user));
        categories.add(new Category("Educação", "education", TransactionType.EXPENSE, user));
        categories.add(new Category("Lazer", "fun", TransactionType.EXPENSE, user));
        categories.add(new Category("Mercado", "grocery", TransactionType.EXPENSE, user));
        categories.add(new Category("Roupas", "clothes", TransactionType.EXPENSE, user));
        categories.add(new Category("Transporte", "transport", TransactionType.EXPENSE, user));
        categories.add(new Category("Outro", "other", TransactionType.EXPENSE, user));

        categoriesRepo.saveAll(categories);

        return user;
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

        String accessToken = jwtService.generateToken(auth);

        return new LoginResponse(accessToken);
    }    
}
