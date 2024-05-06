package com.lemonchat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.lemonchat.dtos.AuthDto;
import com.lemonchat.services.AccountService;
import com.lemonchat.services.AuthService;
import com.lemonchat.util.ValidationUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private AuthService authService;
	
	@Autowired
    private AccountService accountService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthDto> validateGoogleLogin(@RequestBody AuthDto authDto) {
		boolean isTokenValid = ValidationUtil.isAccessTokenValid(authDto.getAccessToken());
		if(!isTokenValid) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access Token is not valid for application.");
		}
		return ResponseEntity.ok(authDto);
	}
	
}
