package com.alaazamelDev.customer.services;

import com.alaazamelDev.customer.models.Customer;
import com.alaazamelDev.customer.repositories.CustomerRepository;
import com.alaazamelDev.customer.requests.RegisterCustomerRequest;
import com.alaazamelDev.exceptions.EmailAlreadyExistsException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Tag("services")
@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldReturnRegisteredCustomerWhenValidRequestIsProvided() {

        // Arrange
        UUID fixedUUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
        LocalDateTime creationTime = LocalDateTime.now();
        Customer newCustomer = Customer.builder()
                .firstName("Alaa")
                .lastName("Zamel")
                .email("alaa.zamel80@gmail.com")
                .build();
        RegisterCustomerRequest request = RegisterCustomerRequest.builder()
                .firstName("Alaa")
                .lastName("Zamel")
                .email("alaa.zamel80@gmail.com")
                .build();
        Customer customerWithId = Customer.builder()
                .id(fixedUUID)
                .firstName("Alaa")
                .lastName("Zamel")
                .email("alaa.zamel80@gmail.com")
                .createdAt(creationTime)
                .build();
        when(customerRepository.save(any(Customer.class)))
                .thenReturn(customerWithId); /* using argument matchers */

        // Act
        Customer savedCustomer = customerService.register(request);

        // Assert
        verify(customerRepository).save(newCustomer);
        assertThat(savedCustomer)
                .as("Check that the returned entity is not null")
                .isNotNull();
        assertThat(savedCustomer.getId())
                .as("Check that the customer ID matches the expected value")
                .isEqualTo(customerWithId.getId());
        assertThat(savedCustomer.getFirstName())
                .as("Check that the first name matches")
                .isEqualTo("Alaa");
        assertThat(savedCustomer.getLastName())
                .as("Check that the last name matches")
                .isEqualTo("Zamel");
        assertThat(savedCustomer.getEmail())
                .as("Check that the email matches")
                .isEqualTo("alaa.zamel80@gmail.com");
        assertThat(savedCustomer.getCreatedAt())
                .as("Check that the creation time has been generated")
                .isNotNull();
    }

    @Test
    void shouldThrowEmailAlreadyExistWhenRegisteringWithDuplicatedEmail() {


        // Arrange
        Customer customer = Customer.builder()
                .email("alaa.zamel80@gmail.com")
                .build();
        RegisterCustomerRequest request = RegisterCustomerRequest.builder()
                .email("alaa.zamel80@gmail.com")
                .build();
        when(customerRepository.save(customer))
                .thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThatThrownBy(() -> customerService.register(request))
                .as("Check if it handles duplicated email exception")
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessageContaining("Email already exists");

    }
}