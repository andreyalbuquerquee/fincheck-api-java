package com.fincheck.fincheckapijava.services;

import java.sql.Timestamp;
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

        validateEntitiesOwnership(user.get(), createTransactionDto.bankAccountId(), createTransactionDto.categoryId());

        Optional<BankAccount> bankAccount = bankAccountsRepo.findById(UUID.fromString(createTransactionDto.bankAccountId()));
        Optional<Category> category = categoriesRepo.findById(UUID.fromString(createTransactionDto.categoryId()));
        
        Transaction transaction = new Transaction(createTransactionDto, user.get(), bankAccount.get(), category.get());
        
        return transactionsRepo.save(transaction);
    }
    
    public Object findAllByUserId(
        String accessToken, 
        int month, 
        int year, 
        String bankAccountId, 
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

        Timestamp startDate = new Timestamp(calendarDate.getTimeInMillis());
        calendarDate.add(Calendar.MONTH, 1);
        Timestamp endDate = new Timestamp(calendarDate.getTimeInMillis());
        

        return transactionsRepo.findByUserAndDate(currentUserId, startDate, endDate);
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
