package com.marcoservices.customer.clients.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FraudCheckResponse(@JsonProperty("data") Boolean isFraudster) {

}
