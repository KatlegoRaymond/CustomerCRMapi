package com.bitroot.CustomerCRMapi.service.impl;

import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.exception.ResourceNotFoundException;
import com.bitroot.CustomerCRMapi.model.Customer;
import com.bitroot.CustomerCRMapi.repository.CustomerRepository;
import com.bitroot.CustomerCRMapi.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;

    public CustomerServiceImpl(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public List<Customer> findByFirstName(String firstName) {
        return customerRepo.findByFirstNameContainingIgnoreCase(firstName);
                //.orElseThrow(() -> new ResourceNotFoundException("Customer not found with Email: " + firstName));
    }

    public List<Customer> findByLastName(String lastName) {
        return null;
    }


    // Save a new customer
    @Override
    public Customer createCustomer(CustomerDto dto) {
        Customer customer = new Customer();
        customer.setFirstName(dto.getFirstName());
        customer.setLastName(dto.getLastName());
        customer.setEmail(dto.getEmail());
        return customerRepo.save(customer);
    }

    // Get customer by ID or throw custom exception
    @Override
    public Customer getCustomerById(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));
    }

    // Get all customers
    @Override
    public List<Customer> getAllCustomers() {

        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        // Find the customer, or throw an exception if not found
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        // Update the fields
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());

        // Save and return the updated customer
        return customerRepo.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        // Check if the customer exists
        Customer customer = customerRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + id));

        // Delete the customer
        customerRepo.delete(customer);
    }

    //Extra Functionalities
    @Override
    public Customer getCustomerByEmail(String email) {
        // Find the customer, or throw an exception if not found
        Optional<Customer> customer = customerRepo.findByEmail(email);

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("No customers found with email: " + email);
        }

        return customer.get();
    }

    @Override
    public List<Customer> getCustomerByFirstName(String name) {
        // Find the customer, or throw an exception if not found
        List<Customer> customer = customerRepo.findByFirstNameContainingIgnoreCase(name);

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("No customers found with first name: " + name);
        }

        return customer;
    }

    @Override
    public List<Customer> getCustomerByLastName(String name) {
        // Find the customer, or throw an exception if not found
        List<Customer> customer = customerRepo.findByLastNameContainingIgnoreCase(name);

        if (customer.isEmpty()) {
            throw new ResourceNotFoundException("No customers found with last name: " + name);
        }

        return customer;
    }


}
