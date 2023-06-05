package com.dabel.easybank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.TransactionViewDTO;
import com.dabel.easybank.mapper.TransactionViewMapper;
import com.dabel.easybank.repository.TransactionViewRepository;

@Service
public class TransactionViewService {

	@Autowired
	private TransactionViewRepository trViewRepository;
	
	public List<TransactionViewDTO> findAll(int userId) {
		
		return trViewRepository.findAllByUserId(userId).stream()
				.map(TransactionViewMapper::entityToDto)
				.collect(Collectors.toList());
	}
}
