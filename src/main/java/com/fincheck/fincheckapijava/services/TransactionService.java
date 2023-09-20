package com.fincheck.fincheckapijava.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.exceptions.NotFoundException;
import com.fincheck.fincheckapijava.model.BankAccount;
import com.fincheck.fincheckapijava.model.Category;
import com.fincheck.fincheckapijava.model.Transaction;
import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.BankAccountRepository;
import com.fincheck.fincheckapijava.repository.CategoriesRepository;
import com.fincheck.fincheckapijava.repository.TransactionsRepository;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.shared.dtos.TransactionDto;

@Service
public class TransactionService {
    
    @Autowired
    UsersRepository usersRepo;

    @Autowired
    BankAccountRepository bankAccountsRepo;

    @Autowired
    CategoriesRepository categoriesRepo;

    @Autowired
    TransactionsRepository transactionsRepo;

    @Autowired
    JWTService jwtService;
    
    public Transaction create(String accessToken, TransactionDto createTransactionDto) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        Optional<User> user = usersRepo.findById(currentUserId.get());

        validateEntitiesOwnership(user.get(), createTransactionDto.bankAccountId(), createTransactionDto.categoryId());

        Optional<BankAccount> bankAccount = bankAccountsRepo.findById(UUID.fromString(createTransactionDto.bankAccountId()));
        Optional<Category> category = categoriesRepo.findById(UUID.fromString(createTransactionDto.categoryId()));
        
        Transaction transaction = new Transaction(createTransactionDto, user.get(), bankAccount.get(), category.get());
        
        return transactionsRepo.save(transaction);
    }

    private void validateEntitiesOwnership(User user, String bankAccountId, String categoryId) {
        Optional<Category> category = categoriesRepo.findByIdAndUser(UUID.fromString(categoryId), user);

        if (!category.isPresent()) {
            throw new NotFoundException("Category not found!");
        }

        Optional<BankAccount> bankAccount = bankAccountsRepo.findByIdAndUser(UUID.fromString(bankAccountId), user);

        if (!bankAccount.isPresent()) {
            throw new NotFoundException("Bank Account not found!");
        }
    }
}
