package com.dabel.easybank.service;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthentificationService {
	
	private SecurityContextRepository securityContextRepository =
	        new HttpSessionSecurityContextRepository();

	public Authentication authenticate(UserDTO userDTO,
			HttpServletRequest request, HttpServletResponse response) {
		
		SecurityContext context = SecurityContextHolder.createEmptyContext();
		Authentication authentication = 
				new TestingAuthenticationToken(userDTO.getEmail(), userDTO.getPassword(), "USER");
		
		context.setAuthentication(authentication);
		SecurityContextHolder.setContext(context);
		securityContextRepository.saveContext(context, request, response);
	
		
		return authentication;
	}
	
	public Authentication getAuthentication() {
		
		return SecurityContextHolder.getContext().getAuthentication();		
	}
	
	public boolean isAuthenticated() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		return auth != null;
	}
	
}
