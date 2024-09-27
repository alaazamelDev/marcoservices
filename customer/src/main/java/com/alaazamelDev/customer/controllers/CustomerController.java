package com.alaazamelDev.customer.controllers;

import com.alaazamelDev.customer.requests.RegisterCustomerRequest;
import com.alaazamelDev.customer.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public void registerCustomer(@RequestBody RegisterCustomerRequest request) {
        log.info("new customer registration request: {}", request);

        // use the service
        customerService.register(request);
    }
}
