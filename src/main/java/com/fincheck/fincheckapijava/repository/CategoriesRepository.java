package com.fincheck.fincheckapijava.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fincheck.fincheckapijava.model.Category;
import com.fincheck.fincheckapijava.model.User;

public interface CategoriesRepository extends JpaRepository<Category, UUID> {
    List<Category> findAllByUser(User user);
    Optional<Category> findByIdAndUser(UUID id, User user);
}
