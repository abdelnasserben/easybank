package com.dabel.easybank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dabel.easybank.helper.Helper;
import com.dabel.easybank.model.Email;
import com.dabel.easybank.model.User;
import com.dabel.easybank.service.AuthentificationService;
import com.dabel.easybank.service.SendEmailService;
import com.dabel.easybank.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

@Controller
public class RegisterController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthentificationService authentificationService;
	
	@Autowired
	private SendEmailService sendEmailService;
	

	@GetMapping("/register")
	public String register(User user) {
		
		if(authentificationService.isAuthenticated())
			return "redirect:/dashboard";
		
		return "register";
	}
	
	@PostMapping("/register")
	public String registerUser(@Valid User user, BindingResult binding, RedirectAttributes redirect) {

		if(binding.hasErrors())
			return "register";
		
		
		if(userService.exists(user.getEmail())) {
			
			String emailField = "email";
			binding.rejectValue(emailField, null, "Email already exists !");
			
			return "register";
		}
		
		
		String passwordField = "password";
		String passwordConfirmField = "passwordConfirm";
		
		if(!binding.getFieldValue(passwordField).equals(binding.getFieldValue(passwordConfirmField))) {

			binding.rejectValue(passwordField, null, "Different passwords");
			binding.rejectValue(passwordConfirmField, null, "Different passwords");
			return "register";
		}
		
		
		//TODO: Generate token and code for user registration
		String token = Helper.generateToken();
		String code = Helper.generateCode();

		//TODO: update user token and code
		user.setToken(token);
		user.setCode(code);
		
		//TODO: get HTML parsing model
		String body = SendEmailService.confirmEmailBodyParser(user.getToken(), user.getCode());
		
		
		//TODO: Initiate email object
		Email email = new Email(user.getEmail(), "Confirm account", body);
		
		
		try {
			
			//TODO: Send confirmation email and save user
			sendEmailService.sendHtmlEmail(email);
			userService.save(user);
			
		} catch (MessagingException e) {
			
			// TODO inform user that we can not sending the email confirmation
			return "redirect:/register?mailSendingError";
		}
		
		String message = "Your are successfuly registered. Please confirm your email";
		redirect.addFlashAttribute("message", message);
		
		return "redirect:/register";
	}
}
