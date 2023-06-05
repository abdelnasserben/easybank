package com.dabel.easybank.exception;

public class UserNotFoundExcption extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundExcption(String message) {
		super(message);
	}

}
