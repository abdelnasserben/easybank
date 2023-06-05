package com.dabel.easybank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.dto.RoleDTO;
import com.dabel.easybank.mapper.RoleMapper;
import com.dabel.easybank.model.Role;
import com.dabel.easybank.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	
	public List<RoleDTO> getRoleByUsername(String email) {
		return roleRepository.findByUsername(email).stream()
				.map(RoleMapper::entityToDto)
				.collect(Collectors.toList());
	}
	
	public RoleDTO save(RoleDTO roleDTO) {
		
		Role role = roleRepository.save(RoleMapper.dtoToEntity(roleDTO));
		
		return RoleMapper.entityToDto(role);
	}
}
