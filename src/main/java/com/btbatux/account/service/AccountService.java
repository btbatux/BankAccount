package com.btbatux.account.service;

import com.btbatux.account.dto.AccountDto;

import com.btbatux.account.dto.CreateAccountRequest;
import com.btbatux.account.dto.converter.AccountDtoConverter;
import com.btbatux.account.model.Account;
import com.btbatux.account.model.Customer;
import com.btbatux.account.model.Transaction;
import com.btbatux.account.repository.Accountepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class AccountService {

    private final Accountepository accountepository;
    private final CustomerService customerService;
    private final AccountDtoConverter accountDtoConverter;


    public AccountService(Accountepository accountepository,
                          CustomerService customerService,
                          AccountDtoConverter accountDtoConverter) {
        this.accountepository = accountepository;
        this.customerService = customerService;
        this.accountDtoConverter = accountDtoConverter;
    }


    public AccountDto createAccount(CreateAccountRequest request) {
        Customer customer = customerService.
                findCustomerById(request.getCustomerId());
        Account account = new Account(
                customer,
                request.getInitialCredit(),
                LocalDateTime.now());

        if (request.getInitialCredit().compareTo(BigDecimal.ZERO) > 0) {
//            Transaction transaction =
//                    transactionService.initiateMoney(account, request.getInitialCredit());
            Transaction transaction = new Transaction(request.getInitialCredit(), account);
            account.getTransactions().add(transaction);
        }

        return accountDtoConverter.convert(accountepository.save(account));
    }
}
