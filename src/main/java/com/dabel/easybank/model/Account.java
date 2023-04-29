package com.dabel.easybank.model;

import java.time.LocalDateTime;

import com.dabel.easybank.helper.AccountType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts")
public class Account {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int accountId;
	
	private int userId;
	private String accountNumber;
	
	@NotBlank
	private String accountName;
	private String accountType;
	private double balance;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@Transient
	private AccountType accountTypeEnum;
	
	public Account() {
		
	}

}
