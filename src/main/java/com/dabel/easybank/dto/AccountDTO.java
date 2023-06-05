package com.dabel.easybank.dto;

import java.time.LocalDateTime;

import com.dabel.easybank.helper.AccountType;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AccountDTO {
	
	private int accountId;

	private int userId;
	private String accountNumber;
	
	@NotBlank
	private String accountName;
	
	private String accountType;
	private double balance;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	//@Transient
	private AccountType accountTypeEnum;


}
