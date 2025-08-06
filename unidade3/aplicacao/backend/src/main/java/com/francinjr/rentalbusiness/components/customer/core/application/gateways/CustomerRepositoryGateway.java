package com.francinjr.rentalbusiness.components.customer.core.application.gateways;

import com.francinjr.rentalbusiness.commons.core.domain.entities.PaginationResult;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryGateway {

    Customer create(Customer customer);

    Customer update(Customer customer);

    Optional<Customer> findById(Long id);

    Optional<Customer> findByName(String name);

    boolean existsById(Long id);

    void deleteById(Long id);

    PaginationResult<Customer> findAllByName(String name, int page, int size, String orderBy);

    List<String> findAllCustomersName();
}
