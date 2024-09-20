package com.btbatux.account.service;

import com.btbatux.account.dto.CustomerDto;
import com.btbatux.account.dto.converter.CustomerDtoConverter;
import com.btbatux.account.exception.CustomerNotFoundException;
import com.btbatux.account.model.Customer;
import com.btbatux.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;


    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }


    protected Customer findCustomerById(String id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found with id: " + id));
    }



    public CustomerDto getCustomerById(String customerId) {
            return customerDtoConverter.convertToCustomerDto(findCustomerById(customerId));
    }
}
