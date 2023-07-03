package com.dabel.easybank.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.AccountDTO;
import com.dabel.easybank.exception.AccountNotFoundException;
import com.dabel.easybank.mapper.AccountMapper;
import com.dabel.easybank.model.Account;
import com.dabel.easybank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public List<AccountDTO> getAllAccountByUserId(int id) {
		
		return accountRepository.findAllByUserId(id).stream()
				.map(AccountMapper::entityToDto)
				.collect(Collectors.toList());
	}
	
	public AccountDTO save(AccountDTO accountDTO) {
		Account account = accountRepository.save(AccountMapper.dtoToEntity(accountDTO));
		return AccountMapper.entityToDto(account);
	}
	
	public boolean exists(AccountDTO accountDTO) {
		
		Account account = AccountMapper.dtoToEntity(accountDTO);
		
		return accountRepository
				.findByUserIdAndAccountName(
						account.getUserId(), 
						account.getAccountName()
				).isPresent();
	}
	
	public AccountDTO findByAccountNumber(String accounNumber) {
		Account account = accountRepository.findIByAccountNumber(accounNumber)
							.orElseThrow(() -> new AccountNotFoundException("Account not found"));
		
		return AccountMapper.entityToDto(account);
	}
	
	public double totalBalance(int userId) {
		
		Optional<Double> total = accountRepository.totalBalance(userId);
		
		return total.isPresent() ? total.get() : 0;
	}
}
