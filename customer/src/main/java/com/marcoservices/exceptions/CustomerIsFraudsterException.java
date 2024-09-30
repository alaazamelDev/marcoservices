package com.marcoservices.exceptions;

public class CustomerIsFraudsterException extends RuntimeException {

    @Override
    public String getMessage() {
        return "the customer is a fraudster";
    }

    public CustomerIsFraudsterException() {
        super("");
    }
}
