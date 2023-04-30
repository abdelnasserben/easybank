package com.dabel.easybank.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SendEmailConfig {

	@Bean
	JavaMailSender mailConfiguration() {
		
		JavaMailSenderImpl mailSenderImpl = new JavaMailSenderImpl();
		
		mailSenderImpl.setHost("smtp.gmail.com");
		mailSenderImpl.setPort(587);
		mailSenderImpl.setUsername("youraddress@gmail.com");
		mailSenderImpl.setPassword("yourpasswordtoken");
		
		Properties properties = mailSenderImpl.getJavaMailProperties();
		
		properties.put("mail.smtp.auth", "true");
		//properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.starttls.enable", "true");
		//properties.put("mail.smtp.starttls.required", "true");
		
		return mailSenderImpl;
	}
}
