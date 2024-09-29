package com.marcoservices.customer.controllers;

import com.marcoservices.customer.models.Customer;
import com.marcoservices.customer.requests.RegisterCustomerRequest;
import com.marcoservices.customer.services.CustomerService;
import com.marcoservices.exceptions.EmailAlreadyExistsException;
import com.marcoservices.utilities.LocalDateFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("controllers")
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;  // For converting object to json

    @MockBean
    private CustomerService customerService;  // Mock the service layer

    // constants
    private static final String REGISTER_CUSTOMER_ENDPOINT = "/api/v1/customers";

    @Test
    @DisplayName("RegisterCustomer_WithValidRequest_ReturnsSuccessResponse")
    void shouldReturnRegisteredCustomerSuccessResponseCodeWhenValidRequestPassed() throws Exception {

        // Arrange
        LocalDateTime now = LocalDateTime.now();
        UUID staticId = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");

        RegisterCustomerRequest request = RegisterCustomerRequest.builder()
                .firstName("Alaa")
                .lastName("Zamel")
                .email("alaa.zamel@gmail.com")
                .build();
        Customer createdCustomer = Customer.builder()
                .id(staticId)
                .firstName("Alaa")
                .lastName("Zamel")
                .email("alaa.zamel@gmail.com")
                .createdAt(now)
                .build();
        String requestBody = objectMapper.writeValueAsString(request);
        when(customerService.register(any(RegisterCustomerRequest.class)))
                .thenReturn(createdCustomer);

        // Act & Assert
        mockMvc.perform(
                        // request details (acting)
                        post(REGISTER_CUSTOMER_ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody)
                )

                // expectations from response (assertion)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.message").value("customer created successfully"))
                .andExpect(jsonPath("$.data.id").value(staticId.toString()))
                .andExpect(jsonPath("$.data.firstName").value("Alaa"))
                .andExpect(jsonPath("$.data.lastName").value("Zamel"))
                .andExpect(jsonPath("$.data.email").value("alaa.zamel@gmail.com"))
                .andExpect(jsonPath("$.data.createdAt").value(LocalDateFormatter.formatDateTime(now)));

        verify(customerService, times(1))
                .register(any(RegisterCustomerRequest.class)); /* verify that service method called once */
    }

    @Test
    @DisplayName("RegisterCustomer_WithDuplicatedEmailRequest_ReturnsBadRequestResponse")
    void shouldReturnBadRequestWhenRegisteringWithDuplicatedEmail() throws Exception {

        // Arrange
        RegisterCustomerRequest request = RegisterCustomerRequest.builder()
                .firstName("Alaa")
                .lastName("Zamel")
                .email("alaa.zamel@gmail.com")
                .build();
        String parsedRequest = objectMapper.writeValueAsString(request);

        when(customerService.register(any(RegisterCustomerRequest.class)))
                .thenThrow(EmailAlreadyExistsException.class);


        // Act
        mockMvc.perform(
                        // prepare request details
                        post(REGISTER_CUSTOMER_ENDPOINT)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(parsedRequest)
                )

                // Assert
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Email already exists"))
                .andExpect(jsonPath("$.statusCode").value(400));

        verify(customerService, times(1))
                .register(any(RegisterCustomerRequest.class)); /* verify that service method called once */

    }
}