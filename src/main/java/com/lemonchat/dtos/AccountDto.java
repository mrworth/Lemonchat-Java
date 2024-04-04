package com.lemonchat.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class AccountDto {
	private Long accountId;
    @NotBlank(message = "Username field is required.")
	private String username;
	@Email(message = "Email field formatting invalid.")
    @NotBlank(message = "Email field is required.")
	private String email;
}
