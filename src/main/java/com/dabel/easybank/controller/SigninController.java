package com.dabel.easybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dabel.easybank.model.User;
import com.dabel.easybank.service.AuthentificationService;
import com.dabel.easybank.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class SigninController {
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpServletResponse response;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	AuthentificationService authentificationService;
	

	@GetMapping("/signin")
	public String login(User user) {
		
		if(authentificationService.isAuthenticated())
			return "redirect:/app";
		
		return "signin";
	}

	@PostMapping("/signin")
	public String loginUser(@Valid User user,
			BindingResult binding, Model model) {
		
		
		if(binding.hasErrors())
			return "signin";
		
	
		Optional<User> checkUser = userService.getByEmail(user.getEmail());
		
		if(!checkUser.isPresent()) {
			
			String emailField = "email";
			binding.rejectValue(emailField, null, "Email not exists !");
			return "signin";
		}
		
		User currentUser = checkUser.get();
		
		if(currentUser.getVerified() == 0) {
			
			String message = "Your account it's not verified.";
			model.addAttribute("errorMessage", message);
			
			return "signin";
		}
			
		
		if(!userService.validCredentials(currentUser, user.getPassword())) {
			
			String message = "Invalid email and password !";
			model.addAttribute("errorMessage", message);
			
			return "signin";
		}
		
		//we authenticate the user
		authentificationService.authenticate(currentUser, request, response);
		
		
		return "redirect:/app";
	}
}
