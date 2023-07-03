package com.dabel.easybank.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dabel.easybank.dto.RoleDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.mapper.RoleMapper;
import com.dabel.easybank.mapper.UserMapper;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class RoleServiceTest {

	@Autowired
	private RoleService service;
	@Autowired
	private RoleRepository repository;
	@Autowired
	private UserRepository userRepository;
	
	@AfterEach
	void release() {
		repository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	void shouldSaveRole() {
		//GIVEN
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		userRepository.save(UserMapper.dtoToEntity(userDTO));
		
		RoleDTO roleDTO = new RoleDTO("test@gmail.com", "ROLE_USER");
		
		//WHEN
		RoleDTO savedRole = service.save(roleDTO);
		
		//THEN
		assertThat(savedRole.getRoleId()).isGreaterThan(0);
		
	}
	
	@Test
	void shouldRetrieveRoleByUsername() {
		//GIVEN
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		userRepository.save(UserMapper.dtoToEntity(userDTO));
		
		RoleDTO roleDTO = new RoleDTO("test@gmail.com", "ROLE_USER");
		repository.save(RoleMapper.dtoToEntity(roleDTO));
		
		//WHEN
		List<RoleDTO> rolesList = service.getRoleByUsername(roleDTO.getUsername());
		
		//THEN
		assertThat(rolesList.size()).isEqualTo(1);
		
	}
	
}
