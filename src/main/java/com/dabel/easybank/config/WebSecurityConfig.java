package com.dabel.easybank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/app/**").authenticated()
						.anyRequest().permitAll()				
				)
				.anonymous(anonymous -> anonymous
						.disable()
				)
//				.formLogin(login -> login
//						.loginPage("/signin")
//						.loginProcessingUrl("/signinProcess")
//						.usernameParameter("email")
//						.defaultSuccessUrl("/app", true)
//				)
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/signin?logout")
				)
				.build();
				
	}
		
}
