package com.francinjr.rentalbusiness.components.customer.core.application.mappers;

import com.francinjr.rentalbusiness.commons.core.application.DomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.AddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.CustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateAddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;

public class CustomerDomainMapper implements DomainMapper {
    private final AddressDomainMapper addressDomainMapper;

    public CustomerDomainMapper(AddressDomainMapper addressDomainMapper) {
        this.addressDomainMapper = addressDomainMapper;
    }

    public CreateCustomerUseCaseOutput domainToCreateUseCaseOutput(Customer domain) {
        CreateAddressOutput addressOutput = addressDomainMapper.domainToCreateUseCaseOutput(domain.getAddress());

        return new CreateCustomerUseCaseOutput(domain.getId(), domain.getName(), domain.getBirthDate(), domain.getEmail(),
                domain.getCpf(), addressOutput);
    }

    public UpdateCustomerUseCaseOutput domainToUpdateUseCaseOutput(Customer domain) {
        UpdateAddressOutput addressOutput = addressDomainMapper.domainToUpdateUseCaseOutput(domain.getAddress());

        return new UpdateCustomerUseCaseOutput(domain.getId(), domain.getName(), domain.getBirthDate(), domain.getEmail(),
                domain.getCpf(), addressOutput);
    }

    public CustomerUseCaseOutput domainToCustomerUseCaseOutput(Customer domain) {
        AddressOutput addressOutput = addressDomainMapper.domainToUseCaseOutput(domain.getAddress());

        return new CustomerUseCaseOutput(domain.getId(), domain.getName(), domain.getBirthDate(), domain.getEmail(),
                domain.getCpf(), addressOutput);
    }


}
