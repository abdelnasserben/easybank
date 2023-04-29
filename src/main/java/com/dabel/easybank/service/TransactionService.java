package com.dabel.easybank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Transaction;
import com.dabel.easybank.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository trRepository;
	
	
	public Transaction save(Transaction transaction) {
		return trRepository.save(transaction);
	}
}
