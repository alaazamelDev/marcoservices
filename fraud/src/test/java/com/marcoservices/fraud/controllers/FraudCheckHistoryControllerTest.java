package com.marcoservices.fraud.controllers;

import com.marcoservices.fraud.services.FraudCheckHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Tag("controllers")
@WebMvcTest(FraudCheckHistoryController.class)
class FraudCheckHistoryControllerTest {

    // constants
    private static final String IS_FRAUDSTER_CHECK_PATH = "/api/v1/fraud-check";

    @MockBean
    FraudCheckHistoryService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("IsFraudsterCheck_ValidId_ReturnsOkResponse")
    void shouldReturnSuccessWhenValidIdIsProvided() throws Exception {

        // Arrange
        String customerId = "123e4567-e89b-12d3-a456-426614174000";
        when(service.isFraudulentCustomer(any(UUID.class)))
                .thenReturn(false);

        // Act
        mockMvc.perform(
                        get(IS_FRAUDSTER_CHECK_PATH + "/" + customerId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                // Assert
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("checked successfully"))
                .andExpect(jsonPath("$.data").value(false));

        verify(service, times(1))
                .isFraudulentCustomer(UUID.fromString(customerId));
    }
}