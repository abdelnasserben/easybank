package com.dabel.easybank.dto;

import java.time.LocalDateTime;

import com.dabel.easybank.helper.TransactionProvider.ReasonCode;
import com.dabel.easybank.helper.TransactionProvider.Status;

import lombok.Data;

@Data
public class PaymentDTO {

	private int paymentId;
	
	private int accountId;
	private String beneficiary;
	private String beneficiaryAccNo;
	private double amount;
	private String referenceNo;
	private String status;
	private String reasonCode;
	private LocalDateTime createdAt;
	
	public PaymentDTO() {
		
	}
	
	public PaymentDTO(int accountId, String beneficiary, String beneficiaryAccNo, double amount, String referenceNo,
			Status status, ReasonCode reasonCode) {

		this.accountId = accountId;
		this.beneficiary = beneficiary;
		this.beneficiaryAccNo = beneficiaryAccNo;
		this.amount = amount;
		this.referenceNo = referenceNo;
		this.status = status.name();
		this.reasonCode = reasonCode.name();
	}
	
}
