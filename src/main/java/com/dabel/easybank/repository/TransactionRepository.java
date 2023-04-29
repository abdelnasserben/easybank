package com.dabel.easybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dabel.easybank.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

}
