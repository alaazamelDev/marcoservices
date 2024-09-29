package com.alaazamelDev.customer.responses;

import com.alaazamelDev.customer.models.Customer;
import com.alaazamelDev.utilities.AbstractResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerResponse extends AbstractResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String createdAt;
    private String updatedAt;


    public static CustomerResponse mapFromEntity(Customer entity) {
        return new CustomerResponse(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                formatDateTime(entity.getCreatedAt()),
                formatDateTime(entity.getUpdatedAt())
        );
    }
}
