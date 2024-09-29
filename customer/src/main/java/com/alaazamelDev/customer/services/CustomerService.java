package com.alaazamelDev.customer.services;

import com.alaazamelDev.customer.models.Customer;
import com.alaazamelDev.customer.repositories.CustomerRepository;
import com.alaazamelDev.customer.requests.RegisterCustomerRequest;
import com.alaazamelDev.exceptions.EmailAlreadyExistsException;
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
