package com.dabel.easybank.model;

import java.time.LocalDateTime;

import com.dabel.easybank.helper.TransactionProvider.ReasonCode;
import com.dabel.easybank.helper.TransactionProvider.Status;

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
	
	public Payment() {
		
	}
	
	public Payment(int accountId, String beneficiary, String beneficiaryAccNo, double amount, String referenceNo,
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
