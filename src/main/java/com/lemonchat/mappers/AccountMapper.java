package com.lemonchat.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.lemonchat.dtos.AccountDto;
import com.lemonchat.entities.Account;


@Mapper(componentModel="spring")
public interface AccountMapper {
    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    AccountDto accountToAccountDto(Account account);
    Account accountDtoToAccount(AccountDto accountDto);
}
