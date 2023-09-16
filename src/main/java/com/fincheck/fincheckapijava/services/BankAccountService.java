package com.fincheck.fincheckapijava.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
