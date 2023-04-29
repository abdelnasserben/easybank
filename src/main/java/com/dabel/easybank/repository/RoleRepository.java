package com.dabel.easybank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dabel.easybank.model.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
	
	List<Role> findByUsername(String email);
}
