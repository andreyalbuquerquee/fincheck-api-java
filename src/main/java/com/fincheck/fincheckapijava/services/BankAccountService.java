package com.fincheck.fincheckapijava.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fincheck.fincheckapijava.model.Transaction;
import com.fincheck.fincheckapijava.model.enums.TransactionType;
import com.fincheck.fincheckapijava.shared.dtos.BankAccountDto;
import com.fincheck.fincheckapijava.shared.dtos.UpdateBankAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.exceptions.NotFoundException;
import com.fincheck.fincheckapijava.model.BankAccount;
import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.BankAccountRepository;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.shared.dtos.CreateBankAccountDto;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JWTService jwtService;

    public BankAccount create(String accessToken, CreateBankAccountDto createBankAccountDto) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        Optional<User> user = usersRepository.findById(currentUserId.get());

        BankAccount bankAccount = new BankAccount(createBankAccountDto, user.get());
        return bankAccountRepository.save(bankAccount);
    }

    public List<BankAccountDto> findAllByUserId(String accessToken) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        Optional<User> user = usersRepository.findById(currentUserId.get());

        return user.get().getBankAccounts().stream().map(bankAccount -> {
            double totalTransactions = bankAccount.getTransactions().stream().mapToDouble(transaction ->
                    transaction.getType() == TransactionType.INCOME ? transaction.getValue() :
                    -transaction.getValue()).sum();
            double currentBalance = bankAccount.getInitialBalance() + totalTransactions;
            return new BankAccountDto(bankAccount, currentBalance);
        }).toList();

        }


    public BankAccount update(String accessToken, UUID bankAccountId, UpdateBankAccountDto updateBankAccountDto) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        
        Optional<User> user = usersRepository.findById(currentUserId.get());
        validateBankAccountOwnership(user.get(), bankAccountId);
        
        
        BankAccount updatedBankAccount = new BankAccount(updateBankAccountDto, user.get());
        updatedBankAccount.setId(bankAccountId);

        return bankAccountRepository.save(updatedBankAccount);
    }

    public Object delete(String accessToken, UUID bankAccountId) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        
        Optional<User> user = usersRepository.findById(currentUserId.get());
        validateBankAccountOwnership(user.get(), bankAccountId);

        bankAccountRepository.deleteById(bankAccountId);

        return null;
    } 


    private void validateBankAccountOwnership(User user, UUID bankAccountId) throws NotFoundException {
        Optional<BankAccount> bankAccount = bankAccountRepository.findByIdAndUser(bankAccountId, user);

        if(bankAccount.isEmpty()) {
            throw new NotFoundException("Bank account not found");
        }
    }

}
