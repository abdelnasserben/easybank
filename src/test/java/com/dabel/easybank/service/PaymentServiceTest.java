package com.dabel.easybank.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.dabel.easybank.dto.AccountDTO;
import com.dabel.easybank.dto.PaymentDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.helper.AccountType;
import com.dabel.easybank.helper.TransactionProvider;
import com.dabel.easybank.repository.AccountRepository;
import com.dabel.easybank.repository.PaymentRepository;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class PaymentServiceTest {

	@Autowired
	private PaymentService service;
	@Autowired
	private PaymentRepository repository;
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
	
	private PaymentDTO paymentDTO;
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
		
		paymentDTO = new PaymentDTO(
				savedAccount.getAccountId(), 
				"Jean Paul", 
				"860054351", 
				120, 
				"REF4578799", 
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER);
	}
	
	@AfterEach
	void release() {
		repository.deleteAll();
		accountRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	@Test
	void shouldSavePayment() {
		//GIVEN
		
		//WHEN
		PaymentDTO savedPayment = service.save(paymentDTO);
		
		//THEN
		assertThat(savedPayment.getPaymentId()).isGreaterThan(0);
	}
	
	@Test
	void shouldRetrievePaymentsByAccountId() {
		//GIVEN
		PaymentDTO paymentDTO2 = new PaymentDTO(
				savedAccount.getAccountId(), 
				"Steve Johnson", 
				"5087431001", 
				50, 
				"REF098732", 
				TransactionProvider.Status.FAILED,
				TransactionProvider.ReasonCode.INSUFFICIENT_FUNDS);
		
		service.save(paymentDTO);
		service.save(paymentDTO2);
		
		//WHEN
		List<PaymentDTO> paymentsList = service.getPaymentsByAccountId(savedAccount.getAccountId());
		
		//THEN
		assertThat(paymentsList.size()).isEqualTo(2);
	}
}
