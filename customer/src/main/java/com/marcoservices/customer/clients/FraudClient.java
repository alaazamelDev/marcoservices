package com.marcoservices.customer.clients;

import org.springframework.stereotype.Component;

import java.util.UUID;

/***
 * Handles HTTP calls to the Fraud Service
 */
@Component
public interface FraudClient {

    Boolean checkIsFraudster(UUID customerId);
}
