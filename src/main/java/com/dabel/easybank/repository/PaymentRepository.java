package com.dabel.easybank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dabel.easybank.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
	
	List<Payment> findAllByAccountId(int accountId);
}
