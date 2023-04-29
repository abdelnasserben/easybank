package com.dabel.easybank.helper;

import java.util.Random;
import java.util.UUID;

public class Helper {

	public static String generateToken() {
		
		String token = UUID.randomUUID().toString();
		return token;
	}
	
	public static String generateCode() {
		
		Random rand = new Random();
		int bound = 963;
		int code = bound * rand.nextInt(bound);
		return String.valueOf(code);
	}
	
	public static String generateAccountNumber() {
		
		Random rand = new Random();
		int bound = 1000;
		int accountNumber = bound * rand.nextInt(bound);
		return String.valueOf(accountNumber);
	}
}
