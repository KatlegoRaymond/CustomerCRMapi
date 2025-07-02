package com.bitroot.CustomerCRMapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CustomerDto {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    // no-args constructor required for Jackson
    public CustomerDto() {}

    public CustomerDto(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
