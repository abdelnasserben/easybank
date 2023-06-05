package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.AccountDTO;
import com.dabel.easybank.model.Account;

public class AccountMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static AccountDTO entityToDto(Account entity) {
		return mapper.map(entity, AccountDTO.class);
	}

	public static Account dtoToEntity(AccountDTO dto) {	
		return mapper.map(dto, Account.class);
	}

}
