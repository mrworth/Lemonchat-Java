package com.lemonchat.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class ValidationDto {
	private String azp;
	private String aud;
	private String sub;
	private String scope;
	private Long exp;
	private Long expires_in;
	private String email;
	private String email_verified;
}
