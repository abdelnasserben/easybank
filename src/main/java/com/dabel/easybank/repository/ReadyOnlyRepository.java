package com.dabel.easybank.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface ReadyOnlyRepository<T, ID> extends Repository<T, ID> {

}
