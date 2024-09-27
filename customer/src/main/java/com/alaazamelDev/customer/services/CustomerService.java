package com.alaazamelDev.customer.services;

import com.alaazamelDev.customer.models.Customer;
import com.alaazamelDev.customer.repositories.CustomerRepository;
import com.alaazamelDev.customer.requests.RegisterCustomerRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {

    public void register(RegisterCustomerRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        // todo: check if the email is valid
        // todo: check if the email is not taken
        // todo: store customer in db
        customerRepository.save(customer);
    }
}
