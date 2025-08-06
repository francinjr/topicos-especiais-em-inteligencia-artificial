package com.francinjr.rentalbusiness.components.customer.core.application.usecases.update;

import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import com.francinjr.rentalbusiness.commons.core.exceptions.EntityNotFoundException;
import com.francinjr.rentalbusiness.commons.core.exceptions.UniqueFieldValuesAlreadyExistsException;
import com.francinjr.rentalbusiness.commons.infrastructure.adapters.UniqueFieldValidatorAdapter;
import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;
import com.francinjr.rentalbusiness.components.customer.core.application.mappers.CustomerDomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import java.util.List;
import java.util.Optional;

public class UpdateCustomerUseCaseImpl implements UpdateCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;
    private final UniqueFieldValidatorAdapter<Customer> uniqueFieldsValidator;
    private final CustomerDomainMapper customerDomainMapper;

    public UpdateCustomerUseCaseImpl(CustomerRepositoryGateway customerRepositoryGateway,
            UniqueFieldValidatorAdapter<Customer> uniqueFieldsValidator,
            CustomerDomainMapper customerDomainMapper) {
        this.customerRepositoryGateway = customerRepositoryGateway;
        this.uniqueFieldsValidator = uniqueFieldsValidator;
        this.customerDomainMapper = customerDomainMapper;
    }

    @Override
    public UpdateCustomerUseCaseOutput execute(UpdateCustomerUseCaseInput input, Long id) throws Exception {
        Optional<Customer> customerToUpdate = customerRepositoryGateway.findById(id);
        if(customerToUpdate.isEmpty()) {
            throw new EntityNotFoundException("Não foi possível atualizar o cliente, pois não foi encontrado.");
        }

        Address newAddress = new Address(customerToUpdate.get().getAddressId(), input.address().cep(), input.address().street(),
                input.address().additionalInfo(), input.address().neighborhood(),
                input.address().city(), input.address().state(), input.address().number());

        customerToUpdate.get().changeCustomerValues(input.name(), input.birthDate(), input.email(), newAddress);

        List<ValidationField> existingFieldValues = uniqueFieldsValidator.validate(customerToUpdate.get());
        if (!existingFieldValues.isEmpty()) {
            throw new UniqueFieldValuesAlreadyExistsException(existingFieldValues);
        }

        customerToUpdate.get().verifyEligibility();

        Customer updatedCustomer = customerRepositoryGateway.update(customerToUpdate.get());
        return customerDomainMapper.domainToUpdateUseCaseOutput(updatedCustomer);
    }
}
