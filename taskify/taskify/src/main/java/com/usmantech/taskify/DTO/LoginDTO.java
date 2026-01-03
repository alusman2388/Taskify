package com.usmantech.taskify.DTO;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class LoginDTO {
	@NotEmpty
	private String userName;
	@NotEmpty
	private String password;
}
