package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.TransactionDTO;
import com.dabel.easybank.model.Transaction;

public class TransactionMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static TransactionDTO entityToDto(Transaction transaction) {
		return mapper.map(transaction, TransactionDTO.class);
	}

	public static Transaction dtoToEntity(TransactionDTO dto) {	
		return mapper.map(dto, Transaction.class);
	}

}
