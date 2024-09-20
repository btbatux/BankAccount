package com.btbatux.account.dto.converter;

import com.btbatux.account.dto.AccountDto;
import com.btbatux.account.dto.TransactionDto;
import com.btbatux.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;


@Component
public class AccountDtoConverter {

    private final CustomerDtoConverter customerDtoConverter;
    private final TransactionDtoConverter transactionDtoConverter;


    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter, TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;

        this.transactionDtoConverter = transactionDtoConverter;
    }

    public AccountDto convert(Account account) {
        return new AccountDto(account.getId(),
                account.getBalance(),
                account.getCreationDate(),
                customerDtoConverter.convertToAccountCustomer(account.getCustomer()),
                account.getTransactions().
                        stream().
                        map(t -> transactionDtoConverter.convert(t)).
                        collect(Collectors.toSet()));
    }
}
