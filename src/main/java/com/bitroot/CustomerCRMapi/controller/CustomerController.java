package com.bitroot.CustomerCRMapi.controller;

import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.model.Customer;
import com.bitroot.CustomerCRMapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create a new customer
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        System.out.println("Customer Created: " + customerDto); //For testing
        Customer saved = customerService.createCustomer(customerDto);
        return ResponseEntity.ok(saved);
    }

    // Get one customer
    // GET: http://localhost:8080/api/customers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    // Get all customers
    // GET: http://localhost:8080/api/customers
    @GetMapping
    public ResponseEntity<List<Customer>> getAll() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    // Update an existing customer
    // PUT: http://localhost:8080/api/customers/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CustomerDto customerDto) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Delete a customer by ID
    // DELETE: http://localhost:8080/api/customers/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully.");
    }

    //Extra functionalities
    @GetMapping("/email")
    public ResponseEntity<Customer> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping("/firstname")
    public ResponseEntity<List<Customer>> getByFirstName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.getCustomerByFirstName(name));
    }

    @GetMapping("/lastname")
    public ResponseEntity<List<Customer>> getByLastName(@RequestParam String name) {
        return ResponseEntity.ok(customerService.getCustomerByLastName(name));
    }


}
