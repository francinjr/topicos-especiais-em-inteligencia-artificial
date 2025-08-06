package com.francinjr.rentalbusiness.components.customer.core.application.usecases.create;

import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;

public interface CreateCustomerUseCase {
    CreateCustomerUseCaseOutput execute(CreateCustomerUseCaseInput input) throws Exception;
}
