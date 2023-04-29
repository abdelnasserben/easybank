package com.dabel.easybank.model;

import lombok.Data;

@Data
public class Email {

	private String address;
	private String subject;
	private String body;
	
	public Email(String address, String subject, String body) {
		
		this.address = address;
		this.subject = subject;
		this.body = body;
	}
	
}
