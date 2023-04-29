package com.dabel.easybank.helper;

public class TransactionProvider {
	
	public enum Type {
		DEPOSIT, WITHDRAW, PAYMENT, TRANSFER 
	}
	
	public enum Source {
		ONLINE, AGENCY, GAB 
	}
	
	public enum Status {
		COMPLETED, FAILED, PENDING
	}
	
	public enum ReasonCode {
		INSUFFICIENT_FUNDS, SUPPLY, SUPPLIED,
		FUNDING_ACCOUNT, PAY_CUSTOMER,
		CUSTOMER_WITHDRAWAL 
	}
}
