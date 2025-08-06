package com.francinjr.rentalbusiness.components.customer.core.application.usecases.create;

import com.francinjr.rentalbusiness.commons.infrastructure.adapters.UniqueFieldValidatorAdapter;
import com.francinjr.rentalbusiness.components.customer.core.application.mappers.CustomerDomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;
import com.francinjr.rentalbusiness.commons.core.exceptions.UniqueFieldValuesAlreadyExistsException;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import java.util.List;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CustomerRepositoryGateway customerRepositoryGateway;
    private final UniqueFieldValidatorAdapter<Customer> uniqueFieldsValidator;
    private final CustomerDomainMapper customerDomainMapper;

    public CreateCustomerUseCaseImpl(CustomerRepositoryGateway customerRepositoryGateway,
            UniqueFieldValidatorAdapter<Customer> uniqueFieldsValidator,
            CustomerDomainMapper customerDomainMapper) {
        this.customerRepositoryGateway = customerRepositoryGateway;
        this.uniqueFieldsValidator = uniqueFieldsValidator;
        this.customerDomainMapper = customerDomainMapper;
    }

    public CreateCustomerUseCaseOutput execute(CreateCustomerUseCaseInput input) throws Exception {
        Address address = new Address(null, input.address().cep(), input.address().street(),
                input.address().additionalInfo(), input.address().neighborhood(),
                input.address().city(), input.address().state(), input.address().number());

        Customer customer = new Customer(null, input.name(), input.birthDate(), input.email(),
                input.cpf(), address);

        List<ValidationField> existingFieldValues = uniqueFieldsValidator.validate(customer);
        if (!existingFieldValues.isEmpty()) {
            throw new UniqueFieldValuesAlreadyExistsException(existingFieldValues);
        }

        customer.verifyEligibility();

        Customer createdCustomer = customerRepositoryGateway.create(customer);
        return customerDomainMapper.domainToCreateUseCaseOutput(createdCustomer);
    }
}
