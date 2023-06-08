package com.dabel.easybank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.RoleDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.exception.UserNotFoundExcption;
import com.dabel.easybank.mapper.UserMapper;
import com.dabel.easybank.model.User;
import com.dabel.easybank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleService roleService;

	@Override
	public UserDTO getById(int id) throws UserNotFoundExcption {
		
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundExcption("User not found"));
		
		return UserMapper.entityToDto(user);
	}

	@Override
	public UserDTO getByTokenAndCode(String token, String code) throws UserNotFoundExcption {
		
		User user = userRepository.findByTokenAndCode(token, code)
				.orElseThrow(() -> new UserNotFoundExcption("User not found"));
		
		return UserMapper.entityToDto(user);
	}

	@Override
	public UserDTO getByEmail(String email) throws UserNotFoundExcption {
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundExcption("User not found"));
		
		return UserMapper.entityToDto(user);
	}

	@Override
	public UserDTO save(UserDTO userDTO) {
		
		//TODO: check if it's a new user
		if(userDTO.getUserId() <= 0) {
			
			//TODO: encode user password
			userDTO.setPassword(encoder().encode(userDTO.getPassword()));
			
			//TODO: save user
			User user = userRepository.save(UserMapper.dtoToEntity(userDTO));
			
			
			//TODO: create user role and save it
			RoleDTO roleDTO = new RoleDTO(user.getEmail(), "ROLE_USER");
			roleService.save(roleDTO);
			
			return UserMapper.entityToDto(user);
		}
		
		User user = userRepository.save(UserMapper.dtoToEntity(userDTO));
		return UserMapper.entityToDto(user);
	}

	@Override
	public boolean exists(String email) {
		
		return userRepository.findByEmail(email).isPresent();
	}

	@Override
	public boolean validCredentials(UserDTO userDTO, String password) {
		
		return encoder().matches(password, userDTO.getPassword());
	}

	@Override
	public List<RoleDTO> getRoles(UserDTO userDTO) {
		
		return roleService.getRoleByUsername(userDTO.getEmail());
	}

	private PasswordEncoder encoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
