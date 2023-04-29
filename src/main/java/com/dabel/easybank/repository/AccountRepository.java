package com.dabel.easybank.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dabel.easybank.model.Account;


public interface AccountRepository extends JpaRepository<Account, Integer> {

	public Optional<Account> findByUserId(int userId);
	public Optional<Account> findIByAccountNumber(String accountNumber);
	public Optional<Account> findByUserIdAndAccountName(int userId, String accountName);
	public List<Account> findAllByUserId(int userId);
	
	@Query(value = "select sum(balance) as totalBalance from Account where userId = :userId")
	public Optional<Double> totalBalance(@Param("userId") int userId);
}
