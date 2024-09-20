package com.btbatux.account.dto.converter;

import com.btbatux.account.dto.AccountCustomerDto;
import com.btbatux.account.dto.CustomerDto;
import com.btbatux.account.model.Customer;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class CustomerDtoConverter {

    private final CustomerAccountDtoConverter customerAccountDtoConverter;

    public CustomerDtoConverter(CustomerAccountDtoConverter customerAccountDtoConverter) {
        this.customerAccountDtoConverter = customerAccountDtoConverter;
    }


    public AccountCustomerDto convertToAccountCustomer(Customer customer) {
        if (customer == null) {
            return new AccountCustomerDto("", "", "");
        }
        return new AccountCustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getSurname());
    }



    public CustomerDto convertToCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(),
                customer.getName(),
                customer.getSurname(),
                Objects.requireNonNull(customer.getAccounts()).stream().
                        map(customerAccountDtoConverter::convertCustomerAccountDto).
                        collect(Collectors.toSet()));
    }
}

