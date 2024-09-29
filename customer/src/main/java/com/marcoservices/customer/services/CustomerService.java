package com.marcoservices.customer.services;

import com.marcoservices.customer.models.Customer;
import com.marcoservices.customer.repositories.CustomerRepository;
import com.marcoservices.customer.requests.RegisterCustomerRequest;
import com.marcoservices.exceptions.EmailAlreadyExistsException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(CustomerRepository customerRepository) {

    public Customer register(RegisterCustomerRequest request) {

        try {
            Customer customer = Customer.builder()
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .build();

            return customerRepository.save(customer);
        } catch (DataIntegrityViolationException exception) {
            throw new EmailAlreadyExistsException();
        }
    }
}
