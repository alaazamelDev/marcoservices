package com.marcoservices.fraud.controllers;

import com.marcoservices.fraud.services.FraudCheckHistoryService;
import com.marcoservices.utilities.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api/v1/fraud-check")
public record FraudCheckHistoryController(FraudCheckHistoryService service) {


    @GetMapping(path = "{customerId}")
    public ResponseEntity<HashMap<String, Object>> isFraudster(
            @PathVariable("customerId") String customerId
    ) {

        // call the action
        UUID customerUUID = UUID.fromString(customerId);
        Boolean isFraudster = service.isFraudulentCustomer(customerUUID);
        HashMap<String, Object> response = ApiResponse.success("checked successfully", isFraudster);
        return ResponseEntity.ok(response);
    }

}
