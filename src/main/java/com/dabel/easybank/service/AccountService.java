package com.dabel.easybank.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Account;
import com.dabel.easybank.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> getAllAccountByUserId(int id) {
		
		return accountRepository.findAllByUserId(id);
	}
	
	public Account save(Account account) {
		return accountRepository.save(account);
	}
	
	public boolean exists(Account account) {
		return accountRepository
				.findByUserIdAndAccountName(
						account.getUserId(), 
						account.getAccountName()
				).isPresent();
	}
	
	public Optional<Account> findByAccountNumber(String accounNumber) {
		return accountRepository.findIByAccountNumber(accounNumber);
	}
	
	public double totalBalance(int userId) {
		
		Optional<Double> total = accountRepository.totalBalance(userId);
		
		return total.isPresent() ? total.get() : 0;
	}
}
