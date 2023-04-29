package com.dabel.easybank.repository;

import java.util.List;

import com.dabel.easybank.model.TransactionView;

public interface TransactionViewRepository extends ReadyOnlyRepository<TransactionView, Integer>{

	List<TransactionView> findAllByUserId(int userId);
	
}
