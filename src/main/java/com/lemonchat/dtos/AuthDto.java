package com.lemonchat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class AuthDto {
	
	private Long accountId;
	
	private Boolean isTokenValidated;
	
	private String email;
	
	private String username;
	
	private Long expiresAt;
	
	private Long firstIssuedAt;
	
	//bearer token
	private String accessToken;
	
	//id token
	private String  idToken;
}
