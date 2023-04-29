package com.dabel.easybank.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dabel.easybank.model.User;
import com.dabel.easybank.service.AuthentificationService;
import com.dabel.easybank.service.UserService;

@Controller
public class BasicController {

	
	@Autowired
	private AuthentificationService authentificationService;
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/")
	public String home() {
		if(authentificationService.isAuthenticated())
			return "redirect:/app";
		
		return "home";
	}
	

	@GetMapping("/404")
	public String errorPage() {
		return "404";
	}
	
	@GetMapping("/verify")
	public String confirmAccount(@RequestParam(value = "token", required = false) String token, 
								 @RequestParam(value = "code", required = false) String code,
								 Model model, RedirectAttributes redirect) {
		
		if(token == null || token.isBlank() || code == null || code.isBlank())
			return "redirect:/404";
		
		Optional<User> checkUser = userService.getByTokenAndCode(token, code);
		
		if(!checkUser.isPresent())
			return "redirect:/404";
		
		//TODO: update user verification values
		User currentUser = checkUser.get();
		currentUser.setVerified(1);
		currentUser.setVerifiedAt(LocalDateTime.now());
		userService.save(currentUser);
		
		String message = "Your account has been successfuly verified. Please sign in";
		model.addAttribute("user", currentUser);
		redirect.addFlashAttribute("successMessage", message);
		
		return "redirect:/signin";
	}
	
}
