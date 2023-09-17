package com.fincheck.fincheckapijava.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fincheck.fincheckapijava.exceptions.NotFoundException;
import com.fincheck.fincheckapijava.model.BankAccount;
import com.fincheck.fincheckapijava.model.User;
import com.fincheck.fincheckapijava.repository.BankAccountRepository;
import com.fincheck.fincheckapijava.repository.UsersRepository;
import com.fincheck.fincheckapijava.security.JWTService;
import com.fincheck.fincheckapijava.shared.dtos.BankAccountDto;

@Service
public class BankAccountService {
    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JWTService jwtService;

    public BankAccount create(String accessToken, BankAccountDto createBankAccountDto) {
        Optional<UUID> currentUserId = jwtService.activeUserId(accessToken.substring(7));
        Optional<User> user = usersRepository.findById(currentUserId.get());        

        BankAccount bankAccount = new BankAccount(createBankAccountDto, user.get());
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount update(String accessToken, UUID bankAccountId, BankAccountDto updateBankAccountDto) {
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

        if(!bankAccount.isPresent()) {
            throw new NotFoundException("Bank account not found");
        }
    }

}
