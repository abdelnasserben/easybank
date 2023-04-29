package com.dabel.easybank.config;

import java.util.Map;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class WebMvcConfig implements ErrorViewResolver{

	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		// TODO Auto-generated method stub
		
		if(status == HttpStatus.NOT_FOUND || status == HttpStatus.FORBIDDEN)
			return new ModelAndView("redirect:/404");
		
		if(status == HttpStatus.BAD_REQUEST)
			return new ModelAndView("redirect:/expired");
		
		return null;
	}

}
