package com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname;

import com.francinjr.rentalbusiness.commons.core.domain.entities.PaginationResult;

public interface ListCustomerByNameUseCase {
    PaginationResult<CustomerUseCaseOutput> execute(String name, int page, int size);
}
