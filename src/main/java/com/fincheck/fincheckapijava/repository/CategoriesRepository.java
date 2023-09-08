package com.fincheck.fincheckapijava.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fincheck.fincheckapijava.model.Category;

public interface CategoriesRepository extends JpaRepository<Category, UUID> {
    
}
