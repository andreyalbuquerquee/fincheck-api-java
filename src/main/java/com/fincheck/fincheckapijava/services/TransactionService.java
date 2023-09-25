package com.fincheck.fincheckapijava.services;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;
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

        validateEntitiesOwnership(user.get(), UUID.fromString(createTransactionDto.bankAccountId()),
                createTransactionDto.categoryId());

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

        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTimeZone(TimeZone.getDefault());

        calendarDate.set(Calendar.YEAR, year);
        calendarDate.set(Calendar.MONTH, month - 1);
        calendarDate.set(Calendar.DAY_OF_MONTH, 1);

        calendarDate.set(Calendar.HOUR_OF_DAY, 0);
        calendarDate.set(Calendar.MINUTE, 0);
        calendarDate.set(Calendar.SECOND, 0);
        calendarDate.set(Calendar.MILLISECOND, 0);

        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(calendarDate.getTimeInMillis());
        calendarDate.add(Calendar.MONTH, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.setTimeInMillis(calendarDate.getTimeInMillis());

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

    private void validateEntitiesOwnership(User user, UUID bankAccountId, String categoryId) {
        Optional<Category> category = categoriesRepo.findByIdAndUser(UUID.fromString(categoryId), user);

        if (category.isEmpty()) {
            throw new NotFoundException("Category not found!");
        }

        Optional<BankAccount> bankAccount = bankAccountsRepo.findByIdAndUser(bankAccountId, user);

        if (bankAccount.isEmpty()) {
            throw new NotFoundException("Bank Account not found!");
        }
    }


}
