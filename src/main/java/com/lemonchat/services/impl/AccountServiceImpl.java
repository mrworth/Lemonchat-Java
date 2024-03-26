package com.lemonchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemonchat.dtos.AccountDto;
import com.lemonchat.entities.Account;
import com.lemonchat.mappers.AccountMapper;
import com.lemonchat.repositories.AccountRepository;
import com.lemonchat.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);
        account = accountRepository.saveAndFlush(account);
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        return AccountMapper.INSTANCE.accountToAccountDto(account);
    }
}
