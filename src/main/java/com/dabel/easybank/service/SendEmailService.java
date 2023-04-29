package com.dabel.easybank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender sender;
	
	private static final String APP_URL = "http://localhost:9002";
	
	
	public void sendHtmlEmail(Email email) throws MessagingException {
	    
		MimeMessage mimeMessage = sender.createMimeMessage();
		MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);

		//mimeHelper.setFrom(FROM_ADDRESS_EMAIL);
		mimeHelper.setTo(email.getAddress());
		mimeHelper.setSubject(email.getSubject());
	    mimeHelper.setText(email.getBody(), true);

	    sender.send(mimeMessage);
	}
	
	
	public static String confirmEmailBodyParser(String token, String code) {
		
		String url = APP_URL + "/verify?token=" + token + "&code=" + code;
		
		String body = "<!DOCTYPE html>\r\n"
				+ "<html>\r\n"
				+ "    <head>\r\n"
				+ "        <style>\r\n"
				+ "            body {\r\n"
				+ "                display: flex;\r\n"
				+ "                justify-content: center;\r\n"
				+ "                align-items:  center;\r\n"
				+ "                height: 100vh;\r\n"
				+ "                font-family: Cambria, serif;\r\n"
				+ "            }\r\n"
				+ "            .center {\r\n"
				+ "                width: 50%;\r\n"
				+ "                box-shadow: 0 0 1rem #444;\r\n"
				+ "                border-radius: 0.3rem;\r\n"
				+ "                padding: 2rem;\r\n"
				+ "                display: flex;\r\n"
				+ "                flex-direction: column;\r\n"
				+ "                align-items: center;\r\n"
				+ "                text-align: center;\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            .bi {\r\n"
				+ "                color: #198754;\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            a {\r\n"
				+ "                display: block;\r\n"
				+ "                text-decoration: none;\r\n"
				+ "                background-color: #0d6efd;\r\n"
				+ "                width: max-content;\r\n"
				+ "                padding: 0.8rem 1.5rem;\r\n"
				+ "                border-radius: 0.3rem;\r\n"
				+ "                color: white;\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            hr {\r\n"
				+ "                display: block;\r\n"
				+ "                width: 100%;\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            .mb-4 {\r\n"
				+ "                margin-bottom: 1rem;\r\n"
				+ "            }\r\n"
				+ "\r\n"
				+ "            p {\r\n"
				+ "                margin-bottom: 0.1rem;\r\n"
				+ "            }\r\n"
				+ "        </style>\r\n"
				+ "    </head>\r\n"
				+ "    <body>\r\n"
				+ "\r\n"
				+ "        <div class=\"center\">\r\n"
				+ "            <svg xmlns=\"http://www.w3.org/2000/svg\" width=\"40\" height=\"40\" fill=\"currentColor\" class=\"bi bi-check-circle\" viewBox=\"0 0 16 16\">\r\n"
				+ "                <path d=\"M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z\"/>\r\n"
				+ "                <path d=\"M10.97 4.97a.235.235 0 0 0-.02.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-1.071-1.05z\"/>\r\n"
				+ "              </svg>\r\n"
				+ "            <p>Welcome and thank you for choosing</p>\r\n"
				+ "            <h1>Easy Bank</h1>\r\n"
				+ "            <hr>\r\n"
				+ "            <p class=\"mb-4\">Your account has been successfuly created. Click below<br>to confirm your account</p>\r\n"
				+ "            <a href= " + url + " target=\"_blank\" class=\"mb-4\">Confirm Your Account</a>\r\n"
				+ "            <small>CopyRight @Easy-Way Bank</small>\r\n"
				+ "        </div>\r\n"
				+ "\r\n"
				+ "    </body>\r\n"
				+ "</html>\r\n"
				+ "\r\n"
				+ "\r\n"
				+ "";
		
		return body;
		
	}
}
