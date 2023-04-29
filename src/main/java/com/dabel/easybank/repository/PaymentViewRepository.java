package com.dabel.easybank.repository;

import java.util.List;

import com.dabel.easybank.model.PaymentView;

public interface PaymentViewRepository extends ReadyOnlyRepository<PaymentView, Integer> {

	List<PaymentView> findAllByUserId(int userId);
}
