package com.dabel.easybank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Role;
import com.dabel.easybank.model.User;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	

	@Override
	public Optional<User> getById(int id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> getByTokenAndCode(String token, String code) {
		// TODO Auto-generated method stub
		return userRepository.findByTokenAndCode(token, code);
	}

	@Override
	public Optional<User> getByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}

	@Override
	public User save(User user) {
		
		//TODO: check if it's a new user
		if(user.getUserId() <= 0) {
			
			//TODO: encode user password
			user.setPassword(encoder().encode(user.getPassword()));
			
			//TODO: save user
			user = userRepository.save(user);
			
			//TODO: create user role and save it
			Role role = new Role(user.getEmail(), "ROLE_USER");
			roleRepository.save(role);
			
			return user;
		}
		
		
		return userRepository.save(user);
	}

	@Override
	public boolean exists(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email).isPresent();
	}

	@Override
	public boolean validCredentials(User user, String password) {
		// TODO Auto-generated method stub
		return encoder().matches(password, user.getPassword());
	}

	@Override
	public List<Role> getRoles(User user) {
		// TODO Auto-generated method stub
		return roleRepository.findByUsername(user.getEmail());
	}
	
	private PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
