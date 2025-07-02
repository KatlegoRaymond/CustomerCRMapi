package com.bitroot.CustomerCRMapi.exception;

import com.bitroot.CustomerCRMapi.controller.CustomerController;
import com.bitroot.CustomerCRMapi.dto.CustomerDto;
import com.bitroot.CustomerCRMapi.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GlobalExceptionHandlerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

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
        CustomerDto invalidDto = new CustomerDto(); // fields are null

        mockMvc.perform(post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("First name is required")))
                .andExpect(content().string(containsString("Last name is required")))
                .andExpect(content().string(containsString("Email is required")));
    }

    @Test
    void whenUnexpectedException_thenReturn500() throws Exception {
        when(customerService.getCustomerById(anyLong()))
                .thenThrow(new RuntimeException("Unexpected DB error"));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("An error occurred")));
    }
}
