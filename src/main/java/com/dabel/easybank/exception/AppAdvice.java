package com.dabel.easybank.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class AppAdvice {
	
//	development in progress
	
//	@ExceptionHandler(UserNotFoundExcption.class)
//	ModelAndView userNotFoundHandler(HttpServletRequest request, RedirectAttributes redirect) {
//		
//		StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
//	    String url = requestURL.toString();
//	    url = url.substring(url.lastIndexOf("/"));
//
//	    if(url.equalsIgnoreCase("/signin")) {
//	    	
//	    	String message = "Email not exists !";
//			redirect.addFlashAttribute("errorMessage", message);
//			
//			return new ModelAndView("redirect:/signin");
//	    }
//	    
//		return new ModelAndView("redirect:/404");
//	}

}
