package com.btbatux.account.dto.converter;

import com.btbatux.account.dto.TransactionDto;
import com.btbatux.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction transaction) {

        return new TransactionDto(transaction.getId(),
                transaction.getTransactionType(),
                transaction.getAmount(),
                transaction.getTransactionDate());

    }
}
