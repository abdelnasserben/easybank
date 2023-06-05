package com.dabel.easybank.mapper;

import org.modelmapper.ModelMapper;

import com.dabel.easybank.dto.RoleDTO;
import com.dabel.easybank.model.Role;

public class RoleMapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static RoleDTO entityToDto(Role role) {
		return mapper.map(role, RoleDTO.class);
	}

	public static Role dtoToEntity(RoleDTO dto) {	
		return mapper.map(dto, Role.class);
	}

}
