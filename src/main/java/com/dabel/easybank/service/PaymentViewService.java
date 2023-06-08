package com.dabel.easybank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.PaymentViewDTO;
import com.dabel.easybank.mapper.PaymentViewMapper;
import com.dabel.easybank.repository.PaymentViewRepository;

@Service
public class PaymentViewService {

	@Autowired
	private PaymentViewRepository pViewRepository;
	
	public List<PaymentViewDTO> findAll(int userId) {
		
		return pViewRepository.findAllByUserId(userId).stream()
				.map(PaymentViewMapper::entityToDto)
				.collect(Collectors.toList());
	}
}
