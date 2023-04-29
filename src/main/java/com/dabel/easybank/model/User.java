package com.dabel.easybank.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
	@Size(min = 3, message = "Minimum 3 caractères")
	private String firstName;
	
	@Size(min = 3, message = "Minimum 3 caractères")
	private String lastName;
	
	@Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "Email invalid")
	private String email;
	
	@Size(min = 3, message = "Minimum 3 caractères")
	private String password;
	
	@Transient
	private String passwordConfirm;	
	
	@Transient
	List<Role> roles;
	
	private String token;
	private String code;
	private int verified;
	private LocalDateTime verifiedAt;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
