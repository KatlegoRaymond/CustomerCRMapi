package com.bitroot.CustomerCRMapi.controller;

import com.bitroot.CustomerCRMapi.dto.AddressDto;
import com.bitroot.CustomerCRMapi.model.Address;
import com.bitroot.CustomerCRMapi.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public  AddressController(AddressService addressService){

        this.addressService = addressService;
    }

    //Create New Address
    @PostMapping
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressDto addressDto){
        System.out.println("Address Created: " + addressDto); // For testing
        Address saved = addressService.createAddress(addressDto);

        return ResponseEntity.ok(saved);
    }

    // Get one address
    // GET: http://localhost:8080/api/addresses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddress(@PathVariable Long id){
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    // Get all addresses
    // GET: http://localhost:8080/api/addresses
    @GetMapping
    public ResponseEntity<List<Address>> getAll() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    // Update an existing customer
    // PUT: http://localhost:8080/api/addresses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        Address updatedCustomer = addressService.updateAddress(id, addressDto);
        return ResponseEntity.ok(updatedCustomer);
    }

    // Delete an address by ID
    // DELETE: http://localhost:8080/api/addresses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return ResponseEntity.ok("Customer deleted successfully.");
    }

    //Extra functionalities
    @GetMapping("/city")
    public ResponseEntity<List<Address>> getByCity(@RequestParam String city) {
        return ResponseEntity.ok(addressService.getAddressesByCity(city));
    }

    @GetMapping("/postalcode")
    public ResponseEntity<List<Address>> getByPostalCode(@RequestParam String code) {
        return ResponseEntity.ok(addressService.getAddressesByPostalCode(code));
    }

}
