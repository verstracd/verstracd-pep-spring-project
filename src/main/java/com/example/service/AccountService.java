package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account register(Account newAccount){
        return accountRepository.save(newAccount);
    }

    public Account login(Account userAccount){
        Optional<Account> optionalAccount = accountRepository.findByUsernameAndPassword(userAccount.getUsername()
        , userAccount.getPassword());
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }
        return null;
        
    }
}
