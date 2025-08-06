package com.francinjr.rentalbusiness.components.customer.core.application.mappers;

import com.francinjr.rentalbusiness.commons.core.application.DomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.AddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateAddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;

public class AddressDomainMapper implements DomainMapper {

    public CreateAddressOutput domainToCreateUseCaseOutput(Address domain) {
        return new CreateAddressOutput(domain.getId(), domain.getCep(), domain.getStreet(),
                domain.getAdditionalInfo(),
                domain.getNeighborhood(),
                domain.getCity(),
                domain.getState(), domain.getNumber());
    }

    public UpdateAddressOutput domainToUpdateUseCaseOutput(Address domain) {
        return new UpdateAddressOutput(domain.getId(), domain.getCep(), domain.getStreet(),
                domain.getAdditionalInfo(),
                domain.getNeighborhood(),
                domain.getCity(),
                domain.getState(), domain.getNumber());
    }

    public AddressOutput domainToUseCaseOutput(Address domain) {
        return new AddressOutput(domain.getCep(), domain.getStreet(),
                domain.getAdditionalInfo(),
                domain.getNeighborhood(),
                domain.getCity(),
                domain.getState(), domain.getNumber());
    }
}
