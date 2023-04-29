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
@Table(name = "v_payments")
@Immutable
public class PaymentView{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int paymentId;
	
	private int accountId;
	private int userId;
	private String beneficiary;
	private String beneficiaryAccNo;
	private double amount;
	private String status;
	private String referenceNo;
	private String reasonCode;
	private LocalDateTime createdAt;
}
