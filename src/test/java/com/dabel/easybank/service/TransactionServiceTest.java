package com.dabel.easybank.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dabel.easybank.dto.AccountDTO;
import com.dabel.easybank.dto.TransactionDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.helper.AccountType;
import com.dabel.easybank.helper.TransactionProvider;
import com.dabel.easybank.repository.AccountRepository;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.TransactionRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class TransactionServiceTest {

	@Autowired
	private TransactionService service;
	@Autowired
	private TransactionRepository repository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	private AccountDTO savedAccount;
	
	@BeforeEach
	void init() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		
		UserDTO userSaved = userService.save(userDTO);
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setUserId(userSaved.getUserId());
		accountDTO.setAccountName("John Doe");
		accountDTO.setAccountNumber("007120782");
		accountDTO.setAccountType(AccountType.BUSINESS.name());
		accountDTO.setBalance(1000);
		
		savedAccount = accountService.save(accountDTO);
		
	}
	
	@AfterEach
	void release() {
		repository.deleteAll();
		accountRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	void shouldSaveTransaction() {
		//GIVEN
		TransactionDTO transaction = new TransactionDTO(
				savedAccount.getAccountId(), 
				TransactionProvider.Type.DEPOSIT,
				500,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.FUNDING_ACCOUNT
				);
		
		//WHEN
		TransactionDTO savedTransaction = service.save(transaction);
		
		//THEN
		assertThat(savedTransaction.getTransactionId()).isGreaterThan(0);
	}
}
