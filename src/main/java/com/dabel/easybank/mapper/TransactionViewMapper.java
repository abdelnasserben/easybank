package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.TransactionViewDTO;
import com.dabel.easybank.model.TransactionView;

public class TransactionViewMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static TransactionViewDTO entityToDto(TransactionView transaction) {
		return mapper.map(transaction, TransactionViewDTO.class);
	}

	public static TransactionView dtoToEntity(TransactionViewDTO dto) {	
		return mapper.map(dto, TransactionView.class);
	}

}
