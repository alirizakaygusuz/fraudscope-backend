package com.finscope.fraudscope.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finscope.fraudscope.transaction.entity.Transaction;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
