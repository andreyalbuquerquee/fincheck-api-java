package com.fincheck.fincheckapijava.shared.dtos.category;

import com.fincheck.fincheckapijava.model.Category;
import com.fincheck.fincheckapijava.model.enums.TransactionType;

import java.util.UUID;

public record CategoryDto(
        UUID id,
        UUID userId,
        String name,
        String icon,
        TransactionType type
) {
    public CategoryDto(Category category) {
        this(category.getId(), category.getUserId(), category.getName(), category.getIcon(), category.getType());
    }
}
