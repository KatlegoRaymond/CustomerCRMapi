package com.bitroot.CustomerCRMapi.repository;

import com.bitroot.CustomerCRMapi.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByCityContainingIgnoreCase(String city);
    List<Address> findByZipCode(String zipCode);

}
