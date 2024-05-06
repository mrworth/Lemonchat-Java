package com.lemonchat.services;

import org.springframework.stereotype.Service;

import com.lemonchat.dtos.AuthDto;

public interface AuthService {
	AuthDto findByAccountId(Long accountId);
}
