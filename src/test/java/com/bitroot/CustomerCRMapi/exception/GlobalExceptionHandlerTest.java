package com.bitroot.CustomerCRMapi.exception;

import com.bitroot.CustomerCRMapi.controller.CustomerController;
import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;
    private CustomerService customerService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        customerService = mock(CustomerService.class);

        CustomerController customerController = new CustomerController(customerService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(customerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenCustomerNotFound_thenReturn404() throws Exception {
        when(customerService.getCustomerById(1L))
                .thenThrow(new ResourceNotFoundException("Customer not found with ID: 1"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Customer not found with ID: 1"));
    }

    @Test
    void whenInvalidCustomer_thenReturn400WithValidationMessage() throws Exception {
        CustomerDto invalidDto = new CustomerDto(); // all fields null

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("First name is required")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Last name is required")))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Email is required")));
    }


    @Test
    void whenUnexpectedException_thenReturn500() throws Exception {
        when(customerService.getCustomerById(99L))
                .thenThrow(new RuntimeException("Unexpected DB error"));

        mockMvc.perform(get("/api/customers/99"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("An error occurred")));
    }
}
