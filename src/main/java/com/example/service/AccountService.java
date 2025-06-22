package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.BadRequestException;
import com.example.exception.DuplicateUserException;
import com.example.exception.UnauthorizedException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account newAccount){
        Optional<Account> duplicateAccount = accountRepository.findByUsername(newAccount.getUsername());
        if(duplicateAccount.isPresent()){
            throw new DuplicateUserException(newAccount.getUsername());
        } else if(newAccount.getUsername().isBlank()){
            throw new BadRequestException(newAccount.getUsername());
        } else if(newAccount.getPassword().length() < 4){
            throw new BadRequestException("Password is too short: " + newAccount.getPassword());
        }
        return accountRepository.save(newAccount);
    }

    public Account login(Account userAccount){
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(userAccount.getUsername()
        , userAccount.getPassword());

        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            throw new UnauthorizedException(userAccount.getUsername() + " or " + userAccount.getPassword());
        }
        
        
    }
}
