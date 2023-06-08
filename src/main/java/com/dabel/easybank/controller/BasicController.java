package com.dabel.easybank.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dabel.easybank.dto.UserDTO;
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
		
		UserDTO user = userService.getByTokenAndCode(token, code);
		
		
		//TODO: update user verification values
		user.setVerified(1);
		user.setVerifiedAt(LocalDateTime.now());
		userService.save(user);
		
		String message = "Your account has been successfuly verified. Please sign in";
		model.addAttribute("user", user);
		redirect.addFlashAttribute("successMessage", message);
		
		return "redirect:/signin";
	}
	
}
