package com.marcoservices.fraud.services;

import com.marcoservices.fraud.models.FraudCheckHistory;
import com.marcoservices.fraud.repositories.FraudCheckHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public record FraudCheckHistoryService(FraudCheckHistoryRepository repository) {

    /**
     * @param customerId of customer being checked
     * @return the result whether customer is fraud or not
     * @implNote this service can be provided by third-party (will not do it now),
     * but the check operation must be stored in the local db.
     */
    public boolean isFraudulentCustomer(UUID customerId) {
        // store the check operation
        FraudCheckHistory checkOperation = FraudCheckHistory.builder()
                .customerId(customerId)
                .isFraudster(false)
                .build();
        repository.save(checkOperation);

        // return the result
        return false;
    }
}
