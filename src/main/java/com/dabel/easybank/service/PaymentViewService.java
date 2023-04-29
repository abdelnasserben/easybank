package com.dabel.easybank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.PaymentView;
import com.dabel.easybank.repository.PaymentViewRepository;

@Service
public class PaymentViewService {

	@Autowired
	private PaymentViewRepository pViewRepository;
	
	public List<PaymentView> findAll(int userId) {
		
		return pViewRepository.findAllByUserId(userId);
	}
}
