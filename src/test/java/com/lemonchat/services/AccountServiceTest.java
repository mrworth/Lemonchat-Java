package com.lemonchat.services;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lemonchat.dtos.AccountDto;
import com.lemonchat.entities.Account;
import com.lemonchat.mappers.AccountMapper;
import com.lemonchat.repositories.AccountRepository;
import com.lemonchat.services.impl.AccountServiceImpl;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
	@Mock
    private AccountRepository accountRepository;
	
	@Mock
    private AccountMapper accountMapper;
	
	@InjectMocks
	AccountServiceImpl accountService = new AccountServiceImpl();
	
	@Test
	public void testCreateAccount() {
		Account account = new Account(1L,"testusername","test@test.com");
		AccountDto accountDto = new AccountDto();
		
		given(accountRepository.saveAndFlush(account)).willReturn(account);
		
		given(accountMapper.accountToAccountDto(account)).willReturn(accountDto);
        given(accountMapper.accountDtoToAccount(accountDto)).willReturn(account);
        
        accountService.createAccount(accountDto);
        then(accountRepository).should(times(1)).saveAndFlush(account);
	
	}
	
	@Test
	public void testGetAccount() {
		Account account = new Account(1L,"testusername","test@test.com");
		AccountDto accountDto = new AccountDto();
		
		given(accountRepository.findById(1L)).willReturn(Optional.of(account));
		
		given(accountMapper.accountToAccountDto(account)).willReturn(accountDto);
        given(accountMapper.accountDtoToAccount(accountDto)).willReturn(account);
        
        accountService.getAccountById(1L);
        then(accountRepository).should(times(1)).findById(1L);
        
	}
}
