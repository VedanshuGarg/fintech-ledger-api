package com.vedanshu.fintech.repository;

import com.vedanshu.fintech.model.Transaction;
import com.vedanshu.fintech.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumber(String accountNumber);
    List<Transaction> findByAccountNumberAndType(String accountNumber, TransactionType type);
}
