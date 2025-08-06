package com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname;

import com.francinjr.rentalbusiness.commons.core.domain.entities.PaginationResult;
import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;
import com.francinjr.rentalbusiness.components.customer.core.application.mappers.CustomerDomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import java.util.List;

public class ListCustomerByNameUseCaseImpl implements ListCustomerByNameUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;
    private final CustomerDomainMapper customerDomainMapper;

    public ListCustomerByNameUseCaseImpl(CustomerRepositoryGateway customerRepositoryGateway,
            CustomerDomainMapper customerDomainMapper) {
        this.customerRepositoryGateway = customerRepositoryGateway;
        this.customerDomainMapper = customerDomainMapper;
    }

    @Override
    public PaginationResult<CustomerUseCaseOutput> execute(String name, int page, int size) {
        String orderBy = "name";
        PaginationResult<Customer> customers = customerRepositoryGateway.findAllByName(name, page,
                size, orderBy);

        List<CustomerUseCaseOutput> outputList = customers.getContent().stream()
                .map(customerDomainMapper::domainToCustomerUseCaseOutput)
                .toList();

        return new PaginationResult<>(outputList, customers.getTotalElements());
    }
}
