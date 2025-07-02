package com.bitroot.CustomerCRMapi.service.impl;

import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.exception.ResourceNotFoundException;
import com.bitroot.CustomerCRMapi.model.Customer;
import com.bitroot.CustomerCRMapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Create a customer
    @Test
    void createCustomer_shouldSaveAndReturnCustomer() {
        CustomerDto dto = new CustomerDto("John", "Doe", "john@example.com");

        Customer saved = new Customer(1L, "John", "Doe", "john@example.com", null);

        when(customerRepo.save(any(Customer.class))).thenReturn(saved);

        Customer result = customerService.createCustomer(dto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(customerRepo, times(1)).save(any(Customer.class));
    }

    // Get customer by ID
    @Test
    void getCustomerById_shouldReturnCustomer() {
        Customer customer = new Customer(1L, "Jane", "Doe", "jane@example.com", null);
        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerById(1L);

        assertEquals("Jane", result.getFirstName());
    }

    // Get customer by ID - not found
    @Test
    void getCustomerById_notFound_shouldThrowException() {
        when(customerRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(2L));
    }

    // Get all customers
    @Test
    void getAllCustomers_shouldReturnList() {
        List<Customer> customers = Arrays.asList(
                new Customer(1L, "A", "B", "a@example.com", null),
                new Customer(2L, "C", "D", "c@example.com", null)
        );

        when(customerRepo.findAll()).thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(2, result.size());
    }

    // Update customer
    @Test
    void updateCustomer_shouldModifyAndSave() {
        Customer existing = new Customer(1L, "Old", "Name", "old@example.com", null);
        CustomerDto dto = new CustomerDto("New", "Name", "new@example.com");

        when(customerRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(customerRepo.save(any(Customer.class))).thenReturn(existing);

        Customer result = customerService.updateCustomer(1L, dto);

        assertEquals("New", result.getFirstName());
        verify(customerRepo).save(existing);
    }

    // Delete customer
    @Test
    void deleteCustomer_shouldRemoveCustomer() {
        Customer customer = new Customer(1L, "Del", "Me", "del@example.com", null);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));

        customerService.deleteCustomer(1L);

        verify(customerRepo).delete(customer);
    }

    // Get customer by email
    @Test
    void getCustomerByEmail_shouldReturnCustomer() {
        Customer customer = new Customer(1L, "Katlego", "Mpete", "katlego@example.com", null);
        when(customerRepo.findByEmail("katlego@example.com")).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomerByEmail("katlego@example.com");

        assertEquals("Katlego", result.getFirstName());
    }

    // Get customer by email - not found
    @Test
    void getCustomerByEmail_notFound_shouldThrowException() {
        when(customerRepo.findByEmail("none@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerByEmail("none@example.com"));
    }

    // Get customer by first name
    @Test
    void getCustomerByFirstName_shouldReturnList() {
        List<Customer> customers = List.of(new Customer(1L, "Katlego", "Mpete", "kat@example.com", null));
        when(customerRepo.findByFirstNameContainingIgnoreCase("katlego")).thenReturn(customers);

        List<Customer> result = customerService.getCustomerByFirstName("katlego");

        assertEquals(1, result.size());
        assertEquals("Katlego", result.get(0).getFirstName());
    }

    // Get customer by first name - not found
    @Test
    void getCustomerByFirstName_notFound_shouldThrowException() {
        when(customerRepo.findByFirstNameContainingIgnoreCase("ghost")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerByFirstName("ghost"));
    }

    // Get customer by last name
    @Test
    void getCustomerByLastName_shouldReturnList() {
        List<Customer> customers = List.of(new Customer(1L, "Ray", "Smith", "ray@example.com", null));
        when(customerRepo.findByLastNameContainingIgnoreCase("smith")).thenReturn(customers);

        List<Customer> result = customerService.getCustomerByLastName("smith");

        assertEquals(1, result.size());
        assertEquals("Smith", result.get(0).getLastName());
    }

    // Get customer by last name - not found
    @Test
    void getCustomerByLastName_notFound_shouldThrowException() {
        when(customerRepo.findByLastNameContainingIgnoreCase("notfound")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerByLastName("notfound"));
    }
}
