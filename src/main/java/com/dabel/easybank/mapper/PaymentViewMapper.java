package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.PaymentViewDTO;
import com.dabel.easybank.model.PaymentView;

public class PaymentViewMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static PaymentViewDTO entityToDto(PaymentView paymentView) {
		return mapper.map(paymentView, PaymentViewDTO.class);
	}

	public static PaymentView dtoToEntity(PaymentViewDTO dto) {	
		return mapper.map(dto, PaymentView.class);
	}

}
