package com.lemonchat.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Auth {
	
	@Id
	private Long accountId;
	
	private Boolean isTokenValidated;
	
	private String email;
	
	private String username;
	
	private Long expiresAt;
	
	private Long firstIssuedAt;
	
	//bearer token
	private Long accessToken;
	
	//id token
	private Long idToken;
	
}
