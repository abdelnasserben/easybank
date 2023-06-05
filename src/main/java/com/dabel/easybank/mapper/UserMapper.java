package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.UserDTO;
import com.dabel.easybank.model.User;

public class UserMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static UserDTO entityToDto(User user) {
		return mapper.map(user, UserDTO.class);
	}

	public static User dtoToEntity(UserDTO dto) {	
		return mapper.map(dto, User.class);
	}

}
