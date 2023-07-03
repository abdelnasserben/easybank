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

import com.dabel.easybank.dto.AccountDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.exception.AccountNotFoundException;
import com.dabel.easybank.helper.AccountType;
import com.dabel.easybank.repository.AccountRepository;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class AccountServiceTest {

	@Autowired
	private AccountService service;
	@Autowired
	private AccountRepository repository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;	
	
	private AccountDTO accountDTO;
	
	@BeforeEach
	void init() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		
		UserDTO userSaved = userService.save(userDTO);
		
		accountDTO = new AccountDTO();
		accountDTO.setUserId(userSaved.getUserId());
		accountDTO.setAccountName("John Doe");
		accountDTO.setAccountNumber("007120782");
		accountDTO.setAccountType(AccountType.BUSINESS.name());
		accountDTO.setBalance(1000);
	}
	
	@AfterEach
	void release() {
		repository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	void shouldSaveAccount() {
		//GIVEN
		
		//WHEN
		AccountDTO savedAccount = service.save(accountDTO);
		
		//THEN
		assertThat(savedAccount.getAccountId()).isGreaterThan(0);
	}
	
	@Test
	void shouldCheckAccountExists() {
		//GIVEN
		AccountDTO savedAccount = service.save(accountDTO);
		AccountDTO noExistsAccount = accountDTO;
		noExistsAccount.setUserId(100);
		
		//WHEN
		boolean expectedTrueResult = service.exists(savedAccount);
		boolean expectedFalseResult = service.exists(noExistsAccount);
		
		//THEN
		assertTrue(expectedTrueResult);
		assertFalse(expectedFalseResult);
	}
	
	@Test
	void shouldRetrieveAccountByAccountNumber() {
		//GIVEN
		AccountDTO savedAccount = service.save(accountDTO);
		
		//WHEN
		AccountDTO expected = service.findByAccountNumber(savedAccount.getAccountNumber());
		
		//THEN
		assertThat(expected.getAccountName()).isEqualTo(savedAccount.getAccountName());
	}
	
	@Test
	void shouldThrowAccountNotFoundExceptionWhenTryRetrieveAccountByAccountNumber() {
		//GIVEN
		
		//WHEN
		Exception exception = assertThrows(AccountNotFoundException.class, 
				() -> service.findByAccountNumber("00000"));
		
		//THEN
		assertThat(exception.getMessage()).isEqualTo("Account not found");
	}
	
	@Test
	void shouldRetrieveAllAccountsByUserId() {
		//GIVEN
		AccountDTO accountDTO2 = new AccountDTO();
		accountDTO2.setUserId(accountDTO.getUserId());
		accountDTO2.setAccountName("Sarah Hunt");
		accountDTO2.setAccountNumber("00190705");
		accountDTO2.setAccountType(AccountType.BUSINESS.name());
		accountDTO2.setBalance(500);

		service.save(accountDTO);
		service.save(accountDTO2);

		//WHEN
		List<AccountDTO> accounts = service.getAllAccountByUserId(accountDTO.getUserId());

		//THEN
		assertThat(accounts.size()).isEqualTo(2);
	}
	
	@Test
	void shouldRetrieveTotalBalanceOfUserAccounts() {
		//GIVEN
		AccountDTO accountDTO2 = new AccountDTO();
		accountDTO2.setUserId(accountDTO.getUserId());
		accountDTO2.setAccountName("Sarah Hunt");
		accountDTO2.setAccountNumber("00190705");
		accountDTO2.setAccountType(AccountType.BUSINESS.name());
		accountDTO2.setBalance(500);
		
		service.save(accountDTO);
		service.save(accountDTO2);
		
		//WHEN
		double expected = service.totalBalance(accountDTO.getUserId());
		
		//THEN
		assertThat(expected).isEqualTo(1500);
	}
}
