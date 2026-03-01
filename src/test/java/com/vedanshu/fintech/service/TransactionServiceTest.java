package com.vedanshu.fintech.service;

import com.vedanshu.fintech.dto.TransactionRequest;
import com.vedanshu.fintech.model.Transaction;
import com.vedanshu.fintech.model.TransactionType;
import com.vedanshu.fintech.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository repository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldCalculateBalanceCorrectly() {
        String account = "ACC-123";
        Transaction t1 = new Transaction(1L, account, new BigDecimal("1000"), TransactionType.CREDIT, null, "REF1");
        Transaction t2 = new Transaction(2L, account, new BigDecimal("200"), TransactionType.DEBIT, null, "REF2");

        when(repository.findByAccountNumber(account)).thenReturn(List.of(t1, t2));

        BigDecimal balance = service.getBalance(account);

        assertEquals(new BigDecimal("800"), balance);
    }

    @Test
    void shouldThrowException_WhenInsufficientFunds() {
        String account = "ACC-EMPTY";
        when(repository.findByAccountNumber(account)).thenReturn(List.of());

        TransactionRequest request = new TransactionRequest(account, new BigDecimal("500"), "DEBIT");

        // It should throw an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            service.processTransaction(request);
        });

        verify(repository, never()).save(any());
    }

    @Test
    void shouldProcessCreditTransaction() {
        TransactionRequest request = new TransactionRequest("ACC-NEW", new BigDecimal("1000"), "CREDIT");

        when(repository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        Transaction result = service.processTransaction(request);

        assertNotNull(result);
        assertEquals(TransactionType.CREDIT, result.getType());
        assertEquals(new BigDecimal("1000"), result.getAmount());
    }
}
