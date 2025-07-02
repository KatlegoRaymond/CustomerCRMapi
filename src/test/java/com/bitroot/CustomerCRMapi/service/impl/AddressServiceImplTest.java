package com.bitroot.CustomerCRMapi.service.impl;

import com.bitroot.CustomerCRMapi.dto.AddressDto;
import com.bitroot.CustomerCRMapi.exception.ResourceNotFoundException;
import com.bitroot.CustomerCRMapi.model.Address;
import com.bitroot.CustomerCRMapi.model.Customer;
import com.bitroot.CustomerCRMapi.repository.AddressRepository;
import com.bitroot.CustomerCRMapi.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepo;

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private AddressServiceImpl addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test creating an address
    @Test
    void createAddress_shouldSaveAndReturnAddress() {
        AddressDto dto = new AddressDto();
        dto.setStreet("123 Main St");
        dto.setCity("Cape Town");
        dto.setZipCode("8000");
        dto.setCustomerId(1L);

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john@example.com");

        Address saved = new Address(1L, dto.getStreet(), dto.getCity(), dto.getZipCode(), customer);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(addressRepo.save(any(Address.class))).thenReturn(saved);

        Address result = addressService.createAddress(dto);

        assertNotNull(result);
        assertEquals("Cape Town", result.getCity());
        verify(addressRepo).save(any(Address.class));
    }

    // Test finding address by ID
    @Test
    void getAddressById_shouldReturnAddress() {
        Address address = new Address(1L, "123 Main St", "Joburg", "2000", null);
        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        Address result = addressService.getAddressById(1L);

        assertEquals("Joburg", result.getCity());
    }

    // Should throw exception when ID not found
    @Test
    void getAddressById_notFound_shouldThrow() {
        when(addressRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(2L));
    }

    // Test finding all addresses
    @Test
    void getAllAddresses_shouldReturnList() {
        List<Address> addresses = Arrays.asList(
                new Address(1L, "1st St", "PTA", "0001", null),
                new Address(2L, "2nd St", "JHB", "2000", null)
        );
        when(addressRepo.findAll()).thenReturn(addresses);

        List<Address> result = addressService.getAllAddresses();

        assertEquals(2, result.size());
    }

    // Test updating an address
    @Test
    void updateAddress_shouldModifyAndSave() {
        Address existing = new Address(1L, "Old", "City", "0000", null);
        AddressDto dto = new AddressDto();
        dto.setStreet("New St");
        dto.setCity("New City");
        dto.setZipCode("9999");
        dto.setCustomerId(1L); // optional

        when(addressRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(addressRepo.save(any(Address.class))).thenReturn(existing);

        Address result = addressService.updateAddress(1L, dto);

        assertEquals("New City", result.getCity());
        verify(addressRepo).save(existing);
    }

    // Test deleting an address
    @Test
    void deleteAddress_shouldRemoveAddress() {
        Address address = new Address(1L, "Delete St", "City", "1111", null);

        when(addressRepo.findById(1L)).thenReturn(Optional.of(address));

        addressService.deleteAddress(1L);

        verify(addressRepo).delete(address);
    }

    // Test getAddressesByCity - found
    @Test
    void getAddressesByCity_shouldReturnList() {
        Address address1 = new Address(1L, "123 Main St", "Cape Town", "8000", null);
        Address address2 = new Address(2L, "456 Loop St", "Cape Town", "8001", null);
        List<Address> mockList = List.of(address1, address2);

        // Return List<Address> directly (not Optional)
        when(addressRepo.findByCityContainingIgnoreCase("Cape Town")).thenReturn(mockList);

        List<Address> result = addressService.getAddressesByCity("Cape Town");

        assertEquals(2, result.size());
        assertEquals("Cape Town", result.get(0).getCity());
    }

    // Test getAddressesByCity - not found
    @Test
    void getAddressesByCity_notFound_shouldThrow() {
        // Return empty List<Address> to simulate not found (not Optional.empty)
        when(addressRepo.findByCityContainingIgnoreCase("noplace")).thenReturn(List.of());

        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressesByCity("noplace"));
    }

    // Test getAddressesByPostalCode
    @Test
    void getAddressesByPostalCode_shouldReturnList() {
        List<Address> addresses = List.of(new Address(1L, "Postal St", "City", "1234", null));
        when(addressRepo.findByZipCode("1234")).thenReturn(addresses);

        List<Address> result = addressService.getAddressesByPostalCode("1234");

        assertEquals(1, result.size());
        assertEquals("1234", result.get(0).getZipCode());
    }
}
