package com.marcoservices.customer.clients.implementation;

import com.marcoservices.customer.clients.responses.FraudCheckResponse;
import com.marcoservices.exceptions.CustomerIsFraudsterException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@Tag("services")
@ExtendWith(MockitoExtension.class)
class FraudClientImplTest {

    // constants
    private final static String IS_FRAUDSTER_ENDPOINT = "http://localhost:8081/api/v1/fraud-check";

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FraudClientImpl fraudClient;

    @Test
    @DisplayName("Should return false when customer id is not fraudster")
    void shouldReturnFalseWhenCustomerIdIsNotFraudster() {

        // Arrange
        UUID fixedUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        when(restTemplate.getForObject(anyString(), any()))
                .thenReturn(new FraudCheckResponse(false));

        // Act
        Boolean isFraudster = fraudClient.checkIsFraudster(fixedUUID);

        // Assert
        assertThat(isFraudster)
                .as("Check that result matching the expected")
                .isEqualTo(false);

    }

    @Test
    @DisplayName("Should throw CustomerIsFraudsterException when customer is fraudster")
    void shouldThrowCustomerIsFraudsterExceptionWhenCustomerIsFraudster() {
        // Arrange
        UUID fixedUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        String strCustomerId = fixedUUID.toString();
        String completeUrl = IS_FRAUDSTER_ENDPOINT + "/" + strCustomerId;
        when(restTemplate.getForObject(completeUrl, FraudCheckResponse.class))
                .thenReturn(new FraudCheckResponse(true));

        // Act & Assert
        assertThatThrownBy(() -> fraudClient.checkIsFraudster(fixedUUID))
                .as("Check that it handles the fraud customer exception")
                .isInstanceOf(CustomerIsFraudsterException.class);
        verify(restTemplate, times(1))
                .getForObject(completeUrl, FraudCheckResponse.class);
    }

    @Test
    @DisplayName("Should throw IllegalStateException when there is a network problem")
    void shouldThrowIllegalStateExceptionWhenCustomerIsFraudster() {
        // Arrange
        UUID fixedUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        String strCustomerId = fixedUUID.toString();
        String completeUrl = IS_FRAUDSTER_ENDPOINT + "/" + strCustomerId;
        when(restTemplate.getForObject(completeUrl, FraudCheckResponse.class))
                .thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> fraudClient.checkIsFraudster(fixedUUID))
                .as("Check that it handles the fraud customer exception")
                .hasMessageContaining("Unknown Error Occurred while checking fraud")
                .isInstanceOf(IllegalStateException.class);
        verify(restTemplate, times(1))
                .getForObject(completeUrl, FraudCheckResponse.class);
    }

}