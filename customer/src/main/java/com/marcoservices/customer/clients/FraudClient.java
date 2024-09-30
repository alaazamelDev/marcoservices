package com.marcoservices.customer.clients;

import java.util.UUID;

/***
 * Handles HTTP calls to the Fraud Service
 */
public interface FraudClient {

    Boolean checkIsFraudster(UUID customerId);
}
