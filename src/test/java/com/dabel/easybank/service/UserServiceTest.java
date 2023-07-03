package com.dabel.easybank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dabel.easybank.dto.RoleDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.exception.UserNotFoundExcption;
import com.dabel.easybank.mapper.UserMapper;
import com.dabel.easybank.model.User;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService service;
	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;
	
	private UserDTO userDTO;
	
	@BeforeEach
	void init() {
		userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		userDTO.setToken("xyz");
		userDTO.setCode("123");
	}
	
	@AfterEach
	void release() {
		roleRepository.deleteAll();
		repository.deleteAll();
	}
	
	@Test
	void shouldSaveUser() {
		//GIVEN
		
		//WHEN
		UserDTO savedUser = service.save(userDTO);
		
		//THEN
		assertThat(savedUser.getUserId()).isGreaterThan(0);
	}
	
	@Test
	void shouldReetrieveUserByUserId() {
		//GIVEN
		User savedUser = repository.save(UserMapper.dtoToEntity(userDTO));
		
		//WHEN
		UserDTO expectedUser = service.getById(savedUser.getUserId());
		
		//THEN
		assertThat(expectedUser.getEmail()).isEqualTo(savedUser.getEmail());
	}
	
	@Test
	void shouldThrowUserNotFoundExceptionWhenTryRetrieveUserByUserId() {
		//GIVEN
		
		//WHEN
		Exception exception = assertThrows(UserNotFoundExcption.class, 
				() -> service.getById(1));
		
		//THEN
		assertThat(exception.getMessage()).isEqualTo("User not found");
	}
	
	@Test
	void shouldRetrieveUserByTokenAndCode() {
		//GIVEN
		User savedUser = repository.save(UserMapper.dtoToEntity(userDTO));
		
		//WHEN
		UserDTO expectedUser = service.getByTokenAndCode(savedUser.getToken(), savedUser.getCode());
		
		//THEN
		assertThat(expectedUser.getEmail()).isEqualTo(savedUser.getEmail());
	}
	
	@Test
	void shouldThrowUserNotFoundExceptionWhenTryRetrieveUserByTokenAndCode() {
		//GIVEN
		
		//WHEN
		Exception exception = assertThrows(UserNotFoundExcption.class, 
				() -> service.getByTokenAndCode("fake token", "fake code"));
		
		//THEN
		assertThat(exception.getMessage()).isEqualTo("User not found");
	}
	
	@Test
	void shouldRetrieveUserByEmail() {
		//GIVEN
		User savedUser = repository.save(UserMapper.dtoToEntity(userDTO));
		
		//WHEN
		UserDTO expectedUser = service.getByEmail(savedUser.getEmail());
		
		//THEN
		assertThat(expectedUser.getEmail()).isEqualTo(savedUser.getEmail());
	}
	
	@Test
	void shouldThrowUserNotFoundExceptionWhenTryRetrieveUserByEmail() {
		//GIVEN
		
		//WHEN
		Exception exception = assertThrows(UserNotFoundExcption.class, 
				() -> service.getByEmail("fakemail@gmail.com"));
		
		//THEN
		assertThat(exception.getMessage()).isEqualTo("User not found");
	}
	
	@Test
	void shouldCheckIfUserExistsOrDoesNotExists() {
		//GIVEN
		User savedUser = repository.save(UserMapper.dtoToEntity(userDTO));
		
		//WHEN
		boolean expectedTrueResult = service.exists(savedUser.getEmail());
		boolean expectedFalseResult = service.exists("fakeemail@gmail.com");
		
		//THEN
		assertTrue(expectedTrueResult);
		assertFalse(expectedFalseResult);
	}
	
	@Test
	void shouldCheckUserValidCredentialsByUserPassword() {
		//GIVEN
		UserDTO savedUser = service.save(userDTO);
		
		//WHEN
		boolean expectedTrueResult = service.validCredentials(savedUser, "123");
		boolean expectedFalseResult = service.validCredentials(savedUser, "000");
		
		//THEN
		assertTrue(expectedTrueResult);
		assertFalse(expectedFalseResult);
	}
	
	@Test
	void shouldRetrieveUserRoles() {
		//GIVEN
		UserDTO userSaved = service.save(userDTO);
		
		//WHEN
		List<RoleDTO> userRoles = service.getRoles(userSaved);
		
		//THEN
		assertThat(userRoles.size()).isEqualTo(1);
		assertThat(userRoles.get(0).getUsername()).isEqualTo(userSaved.getEmail());
	}
	
}
