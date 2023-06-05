package com.dabel.easybank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.RoleDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.exception.UserNotFoundExcption;

@Service
public interface UserService {
	
	UserDTO getById(int id) throws UserNotFoundExcption;
	
	UserDTO getByTokenAndCode(String token, String code) throws UserNotFoundExcption;
	
	UserDTO getByEmail(String email) throws UserNotFoundExcption;
	
	UserDTO save(UserDTO userDTO);
	
	boolean exists(String email);
	
	boolean validCredentials(UserDTO userDTO, String password);
	
	List<RoleDTO> getRoles(UserDTO userDTO);
	

}
