package com.lemonchat.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lemonchat.dtos.AccountDto;
import com.lemonchat.services.AccountService;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper; 
    
    public AccountDto buildAccountDto(Long accountId, String username, String email) {
    	AccountDto accountDto = new AccountDto();
    	accountDto.setAccountId(accountId);
    	accountDto.setUsername(username);
    	accountDto.setEmail(email);
    	return accountDto;
    }
    
    @Test
    public void testCreateAccount() throws Exception {
    	AccountDto accountDto = buildAccountDto(1L,"test","test@test.com");
    	given(accountService.getAccountById(1L)).willReturn(accountDto);
    	mockMvc.perform(post("/accounts")
	    	.contentType(MediaType.APPLICATION_JSON)
	        .content(objectMapper.writeValueAsString(accountDto)))
	    	.andExpect(status().is2xxSuccessful());
    }
    
    @Test
    public void testGetAccount() throws Exception {
    	AccountDto postDto = buildAccountDto(1L,"","");
    	given(accountService.getAccountById(1L)).willReturn(postDto);
        mockMvc.perform(get("/accounts/{accountId}", 1L))
                .andExpect(status().is2xxSuccessful());
    }
}
