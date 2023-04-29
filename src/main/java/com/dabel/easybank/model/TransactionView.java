package com.dabel.easybank.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "v_transactions")
@Immutable
public class TransactionView {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String transactionId;
	
	private int accountId;
	private int userId;
	private String transactionType;
	private double amount;
	private String source;
	private String status;
	private String reasonCode;
	private LocalDateTime createdAt;
}
