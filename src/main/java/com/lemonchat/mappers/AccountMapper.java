package com.lemonchat.mappers;

import org.mapstruct.Mapper;

import com.lemonchat.dtos.AccountDto;
import com.lemonchat.entities.Account;


@Mapper(componentModel="spring")
public interface AccountMapper {
    AccountDto accountToAccountDto(Account account);
    Account accountDtoToAccount(AccountDto accountDto);
}
