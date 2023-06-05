package com.dabel.easybank.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

	private int userId;
	
	@Size(min = 3, message = "minimum 3 characters")
	private String firstName;
	
	@Size(min = 3, message = "minimum 3 characters")
	private String lastName;
	
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email invalid")
	private String email;
	
	@Size(min = 3, message = "minimum 3 characters")
	private String password;
	
	//@Transient
	private String passwordConfirm;
	List<RoleDTO> roles;
	
	private String token;
	private String code;
	private int verified;
	private LocalDateTime verifiedAt;
	private LocalDateTime createdAt;
	
}
