package com.dabel.easybank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dabel.easybank.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	public Optional<User> findByEmail(String email);
	public Optional<User> findByTokenAndCode(String token, String code);

}
