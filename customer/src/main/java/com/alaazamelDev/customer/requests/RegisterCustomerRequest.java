package com.alaazamelDev.customer.requests;

public record RegisterCustomerRequest(
        String firstName,
        String lastName,
        String email
) {
}
