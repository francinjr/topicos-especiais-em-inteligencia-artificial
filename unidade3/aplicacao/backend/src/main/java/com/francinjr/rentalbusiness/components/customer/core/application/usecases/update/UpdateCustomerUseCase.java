package com.francinjr.rentalbusiness.components.customer.core.application.usecases.update;

public interface UpdateCustomerUseCase {
    UpdateCustomerUseCaseOutput execute(UpdateCustomerUseCaseInput input, Long id) throws Exception;
}
