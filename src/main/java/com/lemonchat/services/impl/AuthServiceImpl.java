package com.lemonchat.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lemonchat.dtos.AuthDto;
import com.lemonchat.mappers.AuthMapper;
import com.lemonchat.repositories.AuthRepository;
import com.lemonchat.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
    private AuthMapper authMapper;
	
	@Autowired
    private AuthRepository authRepository;

	@Override
	public AuthDto findByAccountId(Long accountId) {
		return authMapper.authToAuthDto(authRepository.findByAccountId(accountId).get());
	}
	
}
