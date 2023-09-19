package com.fincheck.fincheckapijava.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.model.Category;
import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.CategoriesRepository;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;

@Service
public class CategoryService {
    
    @Autowired
    CategoriesRepository categoriesRepo;

    @Autowired
    UsersRepository usersRepository;
    
    @Autowired
    JWTService jwtService;

    public List<Category> findAllByUserId(String accessToken) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        Optional<User> user = usersRepository.findById(currentUserId.get());

        return categoriesRepo.findAllByUser(user.get());
    }
}
