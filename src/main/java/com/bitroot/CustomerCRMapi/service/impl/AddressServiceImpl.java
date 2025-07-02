package com.bitroot.CustomerCRMapi.service.impl;

import com.bitroot.CustomerCRMapi.dto.AddressDto;
import com.bitroot.CustomerCRMapi.exception.ResourceNotFoundException;
import com.bitroot.CustomerCRMapi.model.Address;
import com.bitroot.CustomerCRMapi.model.Customer;
import com.bitroot.CustomerCRMapi.repository.AddressRepository;
import com.bitroot.CustomerCRMapi.repository.CustomerRepository;
import com.bitroot.CustomerCRMapi.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepo;
    private final CustomerRepository customerRepo;

    public AddressServiceImpl(AddressRepository addressRepo, CustomerRepository customerRepo) {
        this.addressRepo = addressRepo;
        this.customerRepo = customerRepo;
    }


    // Save a new address
    @Override
    public Address createAddress(AddressDto dto) {
        Customer customer = customerRepo.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: "+dto.getCustomerId()));

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setZipCode(dto.getZipCode());
        address.setCustomer(customer); // Set the Customer Relationship

        return addressRepo.save(address);
    }


    // Get address by ID or throw custom exception
    @Override
    public Address getAddressById(Long id)
    {
        return addressRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
    }

    // Get all addresses
    @Override
    public List<Address> getAllAddresses()
    {

        return addressRepo.findAll();
    }

    @Override
    public Address updateAddress(Long id, AddressDto addressDto) {
        // Check if the address exists
        Address address = addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: "+id));

        // Update the fields
        address.setStreet(addressDto.getStreet());
        address.setCity(addressDto.getCity());
        address.setZipCode(addressDto.getZipCode());

        // Save and return the updated address
        return addressRepo.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        // Check if the address exists
        Address address = addressRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Address not found with ID: " + id));

        //Delete the address
        addressRepo.delete(address);
    }

    //Extra Functionalities
    @Override
    public List<Address> getAddressesByCity(String city) {
        List<Address> results = addressRepo.findByCityContainingIgnoreCase(city);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No addresses found with city: " + city);
        }
        return results;
    }

    @Override
    public List<Address> getAddressesByPostalCode(String postalCode) {
        List<Address> results = addressRepo.findByZipCode(postalCode);
        if (results.isEmpty()) {
            throw new ResourceNotFoundException("No addresses found with postal code: " + postalCode);
        }
        return results;
    }


}
