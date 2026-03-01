package com.vedanshu.fintech.service;

import com.vedanshu.fintech.dto.TransactionRequest;
import com.vedanshu.fintech.model.Transaction;
import com.vedanshu.fintech.model.TransactionType;
import com.vedanshu.fintech.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    @Transactional(readOnly = true)
    public BigDecimal getBalance(String accountNumber) {
        List<Transaction> history = repository.findByAccountNumber(accountNumber);

        return history.stream()
                .map(txn -> txn.getType() == TransactionType.CREDIT ? txn.getAmount() : txn.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public Transaction processTransaction(TransactionRequest request) {
        if (TransactionType.valueOf(request.type()) == TransactionType.DEBIT) {
            BigDecimal currentBalance = getBalance(request.accountNumber());
            if (currentBalance.compareTo(request.amount()) < 0) {
                throw new IllegalArgumentException("Insufficient funds for account: " + request.accountNumber());
            }
        }

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(request.accountNumber());
        transaction.setAmount(request.amount());
        transaction.setType(TransactionType.valueOf(request.type()));
        transaction.setReferenceId("TXN-" + System.currentTimeMillis());

        return repository.save(transaction);
    }
}
