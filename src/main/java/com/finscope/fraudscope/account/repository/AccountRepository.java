package com.finscope.fraudscope.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.account.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

}
