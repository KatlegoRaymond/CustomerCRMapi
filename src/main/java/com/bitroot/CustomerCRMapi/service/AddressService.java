package com.bitroot.CustomerCRMapi.service;

import com.bitroot.CustomerCRMapi.dto.AddressDto;
import com.bitroot.CustomerCRMapi.model.Address;

import java.util.List;

public interface AddressService {
    Address createAddress(AddressDto dto);
    Address getAddressById(Long id);
    List<Address> getAllAddresses();
    void deleteAddress(Long id);
    Address updateAddress(Long id, AddressDto addressDto);

    //Extra Functionalities
    List<Address> getAddressesByCity(String city);
    List<Address> getAddressesByPostalCode(String postalCode);
}
