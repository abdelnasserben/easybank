package com.dabel.easybank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dabel.easybank.model.Role;
import com.dabel.easybank.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	
	public List<Role> getRoleByUsername(String email) {
		return roleRepository.findByUsername(email);
	}
}
