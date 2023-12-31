package com.fincheck.fincheckapijava.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.exceptions.NotFoundException;
import com.fincheck.fincheckapijava.model.BankAccount;
import com.fincheck.fincheckapijava.model.Category;
import com.fincheck.fincheckapijava.model.Transaction;
import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.model.enums.TransactionType;
import com.fincheck.fincheckapijava.repository.BankAccountRepository;
import com.fincheck.fincheckapijava.repository.CategoriesRepository;
import com.fincheck.fincheckapijava.repository.TransactionsRepository;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.shared.dtos.transaction.TransactionDto;

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

        validateEntitiesOwnership(user.get(), UUID.fromString(createTransactionDto.bankAccountId()),
                UUID.fromString(createTransactionDto.categoryId()), null);

        Optional<BankAccount> bankAccount =
                bankAccountsRepo.findById(UUID.fromString(createTransactionDto.bankAccountId()));
        Optional<Category> category = categoriesRepo.findById(UUID.fromString(createTransactionDto.categoryId()));

        Transaction transaction = new Transaction(createTransactionDto, user.get(), bankAccount.get(), category.get());

        return transactionsRepo.save(transaction);
    }

    public Object findAllByUserId(
            String accessToken,
            int month,
            int year,
            UUID bankAccountId,
            TransactionType type) {

        UUID currentUserId = jwtService.activeUserId(accessToken.substring(7)).get();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month + 1, 1);

        if (bankAccountId != null && type != null) {
            return transactionsRepo.findByUserAndDateAndBankAccountAndType(currentUserId, startDate, endDate,
                    bankAccountId, type.name());
        }

        if (bankAccountId != null) {
            return transactionsRepo.findByUserAndDateAndBankAccount(currentUserId, startDate, endDate, bankAccountId);
        }

        if (type != null) {
            return transactionsRepo.findByUserAndDateAndType(currentUserId, startDate, endDate, type.name());
        }

        return transactionsRepo.findByUserAndDate(currentUserId, startDate, endDate);
    }

    public Transaction update(String accessToken, UUID transactionId, TransactionDto updateTransactionDto) {
        UUID currentUserId = jwtService.activeUserId(accessToken.substring(7)).get();
        User user = usersRepo.findById(currentUserId).get();

        validateEntitiesOwnership(
                user,
                UUID.fromString(updateTransactionDto.bankAccountId()),
                UUID.fromString(updateTransactionDto.categoryId()),
                transactionId);

        BankAccount bankAccount =
                bankAccountsRepo.findById(UUID.fromString(updateTransactionDto.bankAccountId())).get();

        Category category = categoriesRepo.findById(UUID.fromString(updateTransactionDto.categoryId())).get();

        Transaction transaction = new Transaction(updateTransactionDto, user, bankAccount, category);
        transaction.setId(transactionId);

        return transactionsRepo.save(transaction);
    }

    public Object remove(String accessToken, UUID transactionId) {
        UUID currentUserId = jwtService.activeUserId(accessToken.substring(7)).get();
        User user = usersRepo.findById(currentUserId).get();

        validateEntitiesOwnership(user, null, null, transactionId);

        transactionsRepo.deleteById(transactionId);

        return null;
    }

    private void validateEntitiesOwnership(User user, UUID bankAccountId, UUID categoryId, UUID transactionId) {
        if (categoryId != null) {
            Optional<Category> category = categoriesRepo.findByIdAndUser(categoryId, user);

            if (category.isEmpty()) {
                throw new NotFoundException("Category not found!");
            }
        }

        if(bankAccountId != null) {
            Optional<BankAccount> bankAccount = bankAccountsRepo.findByIdAndUser(bankAccountId, user);

            if (bankAccount.isEmpty()) {
                throw new NotFoundException("Bank Account not found!");
            }
        }

        if (transactionId != null) {
            Optional<Transaction> transaction = transactionsRepo.findByIdAndUser(transactionId, user);

            if (transaction.isEmpty()) throw new NotFoundException("Transaction not found!");
        }

    }


}
