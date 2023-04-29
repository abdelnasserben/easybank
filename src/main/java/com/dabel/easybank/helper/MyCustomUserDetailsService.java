package com.dabel.easybank.helper;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.User;
import com.dabel.easybank.repository.UserRepository;

@Service
public class MyCustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = userRepository.findByEmail(username).orElseThrow(
				() -> new UsernameNotFoundException("Invalid email and password"));
		
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				user.getEmail(), 
				user.getPassword(), 
				Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
				);
		
		return userDetails;
	}

}
