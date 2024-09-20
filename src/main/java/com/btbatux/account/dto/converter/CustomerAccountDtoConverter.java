package com.btbatux.account.dto.converter;

import com.btbatux.account.dto.CustomerAccountDto;
import com.btbatux.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerAccountDtoConverter {

    private final TransactionDtoConverter transactionDtoConverter;

    public CustomerAccountDtoConverter(TransactionDtoConverter transactionDtoConverter) {
        this.transactionDtoConverter = transactionDtoConverter;
    }


    public CustomerAccountDto convertCustomerAccountDto(Account account) {

        return new CustomerAccountDto(
                account.getId(),
                account.getBalance(),
                account.getCreationDate(),
                account.getTransactions().stream().map(transactionDtoConverter::convert).collect(Collectors.toSet()));
    }

}
