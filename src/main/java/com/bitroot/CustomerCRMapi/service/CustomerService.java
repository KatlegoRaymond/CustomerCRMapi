package com.bitroot.CustomerCRMapi.service;

import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.model.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto dto);
    Customer getCustomerById(Long id);
    List<Customer> getAllCustomers();
    void deleteCustomer(Long id);
    Customer updateCustomer(Long id, CustomerDto customerDto);

    Customer getCustomerByEmail(String email);

    List<Customer> getCustomerByFirstName(String name);

    List<Customer> getCustomerByLastName(String name);
}
