package com.dabel.easybank.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "payments")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId;
	
	private int accountId;
	private String beneficiary;
	private String beneficiaryAccNo;
	private double amount;
	private String referenceNo;
	private String status;
	private String reasonCode;
	private LocalDateTime createdAt;
	
}
