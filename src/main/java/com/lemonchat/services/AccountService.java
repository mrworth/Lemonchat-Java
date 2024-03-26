package com.lemonchat.services;

import com.lemonchat.dtos.AccountDto;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);
    AccountDto getAccountById(Long id);
}
