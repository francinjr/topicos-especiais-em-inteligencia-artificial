package com.francinjr.rentalbusiness.components.customer.core.application.usecases.delete;

import com.francinjr.rentalbusiness.commons.core.exceptions.EntityNotFoundException;
import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;

public class DeleteCustomerUseCaseImpl implements DeleteCustomerUseCase {
    private final CustomerRepositoryGateway customerRepositoryGateway;

    public DeleteCustomerUseCaseImpl(CustomerRepositoryGateway customerRepositoryGateway) {
        this.customerRepositoryGateway = customerRepositoryGateway;
    }

    @Override
    public void execute(Long id) {
        boolean existsModel = customerRepositoryGateway.existsById(id);
        if(!existsModel) {
            throw new EntityNotFoundException("Não foi possivel deletar o cliente, pois não existe.");
        }

        customerRepositoryGateway.deleteById(id);
    }
}
