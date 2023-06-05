package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.PaymentDTO;
import com.dabel.easybank.model.Payment;

public class PaymentMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static PaymentDTO entityToDto(Payment payment) {
		return mapper.map(payment, PaymentDTO.class);
	}

	public static Payment dtoToEntity(PaymentDTO dto) {	
		return mapper.map(dto, Payment.class);
	}

}
