package com.lemonchat.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountDto {
	private Long accountId;
    @NotBlank(message = "Name field is required.")
	private String name;
	@Email(message = "Email field should be valid.")
    @NotBlank(message = "Email field is required.")
	private String email;
}
