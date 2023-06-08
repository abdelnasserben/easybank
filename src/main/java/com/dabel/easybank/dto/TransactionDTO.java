package com.dabel.easybank.dto;

import java.time.LocalDateTime;

import com.dabel.easybank.helper.TransactionProvider.ReasonCode;
import com.dabel.easybank.helper.TransactionProvider.Source;
import com.dabel.easybank.helper.TransactionProvider.Status;
import com.dabel.easybank.helper.TransactionProvider.Type;

import lombok.Data;

@Data
public class TransactionDTO {

	private int transactionId;
	
	private int accountId;
	private String transactionType;
	private double amount;
	private String source;
	private String status;
	private String reasonCode;
	private LocalDateTime createdAt;
	
	public TransactionDTO() {
		
	}
	
	public TransactionDTO(int accountId, Type transactionType, double amount, Source source, Status status,
			ReasonCode reasonCode) {

		this.accountId = accountId;
		this.transactionType = transactionType.name();
		this.amount = amount;
		this.source = source.name();
		this.status = status.name();
		this.reasonCode = reasonCode.name();
	}
	
	
}
