package com.dabel.easybank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.TransactionDTO;
import com.dabel.easybank.mapper.TransactionMapper;
import com.dabel.easybank.model.Transaction;
import com.dabel.easybank.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository trRepository;
	
	
	public TransactionDTO save(TransactionDTO transactionDTO) {
		Transaction transaction = trRepository.save(TransactionMapper.dtoToEntity(transactionDTO));
		return TransactionMapper.entityToDto(transaction);
	}
}
