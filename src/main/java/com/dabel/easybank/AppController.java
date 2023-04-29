package com.dabel.easybank;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}
}
