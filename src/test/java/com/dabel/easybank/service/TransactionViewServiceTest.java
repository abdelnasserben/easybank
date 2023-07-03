package com.dabel.easybank.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dabel.easybank.dto.AccountDTO;
import com.dabel.easybank.dto.TransactionDTO;
import com.dabel.easybank.dto.TransactionViewDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.helper.AccountType;
import com.dabel.easybank.helper.TransactionProvider;
import com.dabel.easybank.repository.AccountRepository;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.TransactionRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class TransactionViewServiceTest {

	@Autowired
	private TransactionViewService service;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionRepository transactionRepository;
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
	
	private UserDTO savedUser;
	private AccountDTO savedAccount;
	
	@BeforeEach
	void init() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		
		savedUser = userService.save(userDTO);
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setUserId(savedUser.getUserId());
		accountDTO.setAccountName("John Doe");
		accountDTO.setAccountNumber("007120782");
		accountDTO.setAccountType(AccountType.BUSINESS.name());
		accountDTO.setBalance(1000);
		
		savedAccount = accountService.save(accountDTO);
		
	}
	
	@AfterEach
	void release() {
		transactionRepository.deleteAll();
		accountRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	void shouldRetrieveTransactionsByUserId() {
		//GIVEN
		TransactionDTO transaction1 = new TransactionDTO(
				savedAccount.getAccountId(), 
				TransactionProvider.Type.DEPOSIT,
				500,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.FUNDING_ACCOUNT
				);
		TransactionDTO transaction2 = new TransactionDTO(
				savedAccount.getAccountId(), 
				TransactionProvider.Type.TRANSFER,
				70,
				TransactionProvider.Source.ONLINE,
				TransactionProvider.Status.FAILED,
				TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS
				);
		
		transactionService.save(transaction1);
		transactionService.save(transaction2);
		
		//WHEN
		List<TransactionViewDTO> transactions = service.findAll(savedUser.getUserId());
		
		//THEN
		assertThat(transactions.size()).isEqualTo(2);
	}
}
