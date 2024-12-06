package com.example.banking_app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.banking_app.dto.AccountDto;
import com.example.banking_app.entitiy.Account;
import com.example.banking_app.mapper.AccountMapper;
import com.example.banking_app.repository.AccountRepository;
import com.example.banking_app.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService{

    private AccountRepository accountRepository;

    private AccountServiceImpl(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account= AccountMapper.mapToAccount(accountDto);
        Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
       Account account= accountRepository
       .findById(id).
       orElseThrow(()-> new RuntimeException("Account Does Not Exists"));
       return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account= accountRepository
       .findById(id).
       orElseThrow(()-> new RuntimeException("Account Does Not Exists"));
       double total=  account.getBalance()+amount;
       account.setBalance(total);
       Account savedAccount= accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);

    }

    @Override
    public AccountDto withdraw(Long id, double amount) {

        Account account= accountRepository
       .findById(id).
       orElseThrow(()-> new RuntimeException("Account Does Not Exists"));
       if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Amount");
       }
       double total = account.getBalance()-amount;
       account.setBalance(total);
       Account savedAccount = accountRepository.save(account);

       return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Account Does Not Exists"));
        accountRepository.deleteById(id);
      
    }


    
}
