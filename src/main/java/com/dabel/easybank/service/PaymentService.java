package com.dabel.easybank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Payment;
import com.dabel.easybank.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	
	public Payment save(Payment payment) {
		return paymentRepository.save(payment);
	}
	
	public List<Payment> save(int accountId) {
		return paymentRepository.findAllByAccountId(accountId);
	}
}
