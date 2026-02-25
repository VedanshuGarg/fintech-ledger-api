package com.vedanshu.fintech.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record TransactionRequest(
        @NotNull(message = "Account number is mandatory")
        String accountNumber,

        @NotNull
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotNull(message = "Type must be CREDIT or DEBIT")
        String type
) {}
