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
import com.dabel.easybank.dto.PaymentViewDTO;
import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.helper.AccountType;
import com.dabel.easybank.helper.TransactionProvider;
import com.dabel.easybank.repository.AccountRepository;
import com.dabel.easybank.repository.PaymentRepository;
import com.dabel.easybank.repository.RoleRepository;
import com.dabel.easybank.repository.UserRepository;

@SpringBootTest
public class PaymentViewServiceTest {
	
	@Autowired
	private PaymentViewService service;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private PaymentRepository paymentRepository;
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
	
	private UserDTO savedUserDTO;
	private AccountDTO savedAccountDTO;
	
	@BeforeEach
	void init() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("test@gmail.com");
		userDTO.setPassword("123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		
		savedUserDTO = userService.save(userDTO);
		
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setUserId(savedUserDTO.getUserId());
		accountDTO.setAccountName("John Doe");
		accountDTO.setAccountNumber("007120782");
		accountDTO.setAccountType(AccountType.BUSINESS.name());
		accountDTO.setBalance(1000);
		
		savedAccountDTO = accountService.save(accountDTO);
		
	}
	
	@AfterEach
	void release() {
		paymentRepository.deleteAll();
		accountRepository.deleteAll();
		roleRepository.deleteAll();
		userRepository.deleteAll();
	}
	
	
	@Test
	void shouldRetrieveAllUserPaymentsByUserId() {
		//GIVEN
		PaymentDTO paymentDTO = new PaymentDTO(
				savedAccountDTO.getAccountId(), 
				"Jean Paul", 
				"860054351", 
				120, 
				"REF4578799", 
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER);
		
		PaymentDTO paymentDTO2 = new PaymentDTO(
				savedAccountDTO.getAccountId(), 
				"Sarah Hunt", 
				"007854377", 
				60, 
				"REF005699", 
				TransactionProvider.Status.COMPLETED,
				TransactionProvider.ReasonCode.PAY_CUSTOMER);
		
		paymentService.save(paymentDTO);
		paymentService.save(paymentDTO2);
		
		//WHEN
		List<PaymentViewDTO> expectedList = service.findAll(savedUserDTO.getUserId());
		
		//THEN
		assertThat(expectedList.size()).isEqualTo(2);
		assertThat(expectedList.get(1).getAmount()).isEqualTo(60);
	}

}
