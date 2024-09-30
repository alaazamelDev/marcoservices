package com.marcoservices.customer.clients.implementation;

import com.marcoservices.customer.clients.FraudClient;
import com.marcoservices.customer.clients.responses.FraudCheckResponse;
import com.marcoservices.exceptions.CustomerIsFraudsterException;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@AllArgsConstructor
public class FraudClientImpl implements FraudClient {

    // constants
    private final static String IS_FRAUDSTER_ENDPOINT = "http://localhost:8081/api/v1/feaud-check";

    // dependencies
    private final RestTemplate restTemplate;

    @Override
    public Boolean checkIsFraudster(UUID customerId) {

        // prepare data
        String strCustomerId = customerId.toString();
        String completeUrl = IS_FRAUDSTER_ENDPOINT + "/" + strCustomerId;


        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                completeUrl,
                FraudCheckResponse.class
        );

        if (fraudCheckResponse == null) {
            throw new IllegalStateException("Unknown Error Occurred while checking fraud");
        }

        if (fraudCheckResponse.isFraudster()) {
            throw new CustomerIsFraudsterException();
        }

        return false;
    }
}
