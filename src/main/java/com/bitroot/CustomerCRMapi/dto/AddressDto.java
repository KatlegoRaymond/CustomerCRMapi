package com.bitroot.CustomerCRMapi.dto;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

//creating a separate controller for managing addresses
@Data
public class AddressDto {
    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String zipCode;

    @NotNull
    private Long customerId;

    public AddressDto() {

    }
}
