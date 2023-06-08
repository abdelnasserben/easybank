package com.dabel.easybank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.PaymentDTO;
import com.dabel.easybank.mapper.PaymentMapper;
import com.dabel.easybank.model.Payment;
import com.dabel.easybank.repository.PaymentRepository;

@Service
public class PaymentService {

	@Autowired
	private PaymentRepository paymentRepository;
	
	
	public PaymentDTO save(PaymentDTO paymentDTO) {
		Payment payment = paymentRepository.save(PaymentMapper.dtoToEntity(paymentDTO));
		return PaymentMapper.entityToDto(payment);
	}
	
	public List<PaymentDTO> save(int accountId) {
		return paymentRepository.findAllByAccountId(accountId)
				.stream()
				.map(PaymentMapper::entityToDto)
				.collect(Collectors.toList());
	}
}
