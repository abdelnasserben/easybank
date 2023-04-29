package com.dabel.easybank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.TransactionView;
import com.dabel.easybank.repository.TransactionViewRepository;

@Service
public class TransactionViewService {

	@Autowired
	private TransactionViewRepository trViewRepository;
	
	public List<TransactionView> findAll(int userId) {
		
		return trViewRepository.findAllByUserId(userId);
	}
}
