package com.dabel.easybank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Role;
import com.dabel.easybank.model.User;

@Service
public interface UserService {
	
Optional<User> getById(int id);
	
	Optional<User> getByTokenAndCode(String token, String code);
	
	Optional<User> getByEmail(String email);
	
	User save(User user);
	
	boolean exists(String email);
	
	boolean validCredentials(User user, String password);
	
	List<Role> getRoles(User user);
	

}
