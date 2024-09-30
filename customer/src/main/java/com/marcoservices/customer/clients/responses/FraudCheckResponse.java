package com.marcoservices.customer.clients.responses;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FraudCheckResponse {

    private Boolean data;

    public Boolean isFraudster() {
        return data;
    }
}
