package com.btbatux.account.controller;

import com.btbatux.account.dto.AccountDto;
import com.btbatux.account.dto.CreateAccountRequest;
import com.btbatux.account.repository.CustomerRepository;
import com.btbatux.account.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;
    private final CustomerRepository customerRepository;


    public AccountController(AccountService accountService,
                             CustomerRepository customerRepository) {
        this.accountService = accountService;
        this.customerRepository = customerRepository;
    }


    @PostMapping("/create-account")
    public ResponseEntity<AccountDto> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }
}
