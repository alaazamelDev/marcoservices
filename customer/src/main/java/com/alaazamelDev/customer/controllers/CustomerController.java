package com.alaazamelDev.customer.controllers;

import com.alaazamelDev.customer.models.Customer;
import com.alaazamelDev.customer.requests.RegisterCustomerRequest;
import com.alaazamelDev.customer.responses.CustomerResponse;
import com.alaazamelDev.customer.services.CustomerService;
import com.alaazamelDev.utilities.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("api/v1/customers")
public record CustomerController(CustomerService customerService) {

    @PostMapping
    public ResponseEntity<HashMap<String, Object>> registerCustomer(
            @Valid @RequestBody RegisterCustomerRequest request
    ) {

        // log the request
        log.info("new customer registration request: {}", request);

        try {
            // call the action from the service
            Customer createdCustomer = customerService.register(request);

            // create the response
            CustomerResponse response = CustomerResponse.mapFromEntity(createdCustomer);

            return ResponseEntity.ok(ApiResponse.success(
                    "customer created successfully",
                    response
            ));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(ApiResponse.errorResponse(
                            e.getMessage(),
                            HttpStatus.BAD_REQUEST.value()
                    ));
        }
    }
}
