package com.bitroot.CustomerCRMapi.controller;

import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.model.Customer;
import com.bitroot.CustomerCRMapi.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_shouldReturnSavedCustomer() {
        CustomerDto dto = new CustomerDto("John", "Doe", "john@example.com");
        Customer saved = new Customer(1L, "John", "Doe", "john@example.com", null);

        when(customerService.createCustomer(dto)).thenReturn(saved);

        ResponseEntity<Customer> response = customerController.createCustomer(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saved, response.getBody());
    }

    @Test
    void getCustomer_shouldReturnCustomer() {
        Customer customer = new Customer(1L, "Jane", "Smith", "jane@example.com", null);
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    void getAll_shouldReturnCustomerList() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "Alice", "A", "alice@example.com", null),
                new Customer(2L, "Bob", "B", "bob@example.com", null)
        );

        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(customers, response.getBody());
    }

    @Test
    void updateCustomer_shouldReturnUpdatedCustomer() {
        CustomerDto dto = new CustomerDto("Updated", "User", "updated@example.com");
        Customer updated = new Customer(1L, "Updated", "User", "updated@example.com", null);

        when(customerService.updateCustomer(1L, dto)).thenReturn(updated);

        ResponseEntity<Customer> response = customerController.updateCustomer(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void deleteCustomer_shouldReturnSuccessMessage() {
        doNothing().when(customerService).deleteCustomer(1L);

        ResponseEntity<String> response = customerController.deleteCustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully.", response.getBody());
    }
}
