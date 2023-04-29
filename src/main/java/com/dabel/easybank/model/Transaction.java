package com.dabel.easybank.model;

import java.time.LocalDateTime;

import com.dabel.easybank.helper.TransactionProvider.ReasonCode;
import com.dabel.easybank.helper.TransactionProvider.Source;
import com.dabel.easybank.helper.TransactionProvider.Status;
import com.dabel.easybank.helper.TransactionProvider.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;
	
	private int accountId;
	private String transactionType;
	private double amount;
	private String source;
	private String status;
	private String reasonCode;
	private LocalDateTime createdAt;
	
	public Transaction() {
		
	}
	
	public Transaction(int accountId, Type transactionType, double amount, Source source, Status status,
			ReasonCode reasonCode) {

		this.accountId = accountId;
		this.transactionType = transactionType.name();
		this.amount = amount;
		this.source = source.name();
		this.status = status.name();
		this.reasonCode = reasonCode.name();
	}
	
	
}
