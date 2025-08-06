package com.francinjr.rentalbusiness.components.customer.infrastructure.adapters.mappers;

import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.AddressEntity;
import org.springframework.stereotype.Component;

@Component
public class AddressEntityMapper {

    public AddressEntity modelToEntity(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressEntity(address.getId(), address.getCep(), address.getStreet(),
                address.getAdditionalInfo(), address.getNeighborhood(), address.getCity(),
                address.getState(), address.getNumber());
    }

    public Address entityToModel(AddressEntity entity) {
        if (entity == null) {
            return null;
        }

        return new Address(entity.getId(), entity.getCep(), entity.getStreet(),
                entity.getAdditionalInfo(), entity.getNeighborhood(), entity.getCity(),
                entity.getState(), entity.getNumber());
    }
}