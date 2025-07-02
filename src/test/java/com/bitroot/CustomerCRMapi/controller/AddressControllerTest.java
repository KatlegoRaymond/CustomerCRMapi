package com.bitroot.CustomerCRMapi.controller;

import com.bitroot.CustomerCRMapi.dto.AddressDto;
import com.bitroot.CustomerCRMapi.model.Address;
import com.bitroot.CustomerCRMapi.service.AddressService;
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

class AddressControllerTest {

    @Mock
    private AddressService addressService;

    @InjectMocks
    private AddressController addressController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAddress_shouldReturnSavedAddress() {
        AddressDto dto = new AddressDto();
        dto.setStreet("123 Main St");
        dto.setCity("Springfield");
        dto.setZipCode("12345");
        dto.setCustomerId(1L);

        Address saved = new Address(1L, "123 Main St", "Springfield", "12345", null);

        when(addressService.createAddress(dto)).thenReturn(saved);

        ResponseEntity<Address> response = addressController.createAddress(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saved, response.getBody());
    }

    @Test
    void getAddress_shouldReturnAddress() {
        Address address = new Address(1L, "456 Oak St", "Metropolis", "67890", null);

        when(addressService.getAddressById(1L)).thenReturn(address);

        ResponseEntity<Address> response = addressController.getAddress(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(address, response.getBody());
    }

    @Test
    void getAll_shouldReturnAddressList() {
        List<Address> addresses = Arrays.asList(
                new Address(1L, "123 Main St", "Springfield", "12345", null),
                new Address(2L, "456 Oak St", "Metropolis", "67890", null)
        );

        when(addressService.getAllAddresses()).thenReturn(addresses);

        ResponseEntity<List<Address>> response = addressController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(addresses, response.getBody());
    }

    @Test
    void updateAddress_shouldReturnUpdatedAddress() {
        AddressDto dto = new AddressDto();
        dto.setStreet("789 Pine St");
        dto.setCity("Gotham");
        dto.setZipCode("54321");
        dto.setCustomerId(1L);

        Address updated = new Address(1L, "789 Pine St", "Gotham", "54321", null);

        when(addressService.updateAddress(1L, dto)).thenReturn(updated);

        ResponseEntity<Address> response = addressController.updateAddress(1L, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
    }

    @Test
    void deleteAddress_shouldReturnConfirmationMessage() {
        doNothing().when(addressService).deleteAddress(1L);

        ResponseEntity<String> response = addressController.deleteAddress(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully.", response.getBody());
    }
}
