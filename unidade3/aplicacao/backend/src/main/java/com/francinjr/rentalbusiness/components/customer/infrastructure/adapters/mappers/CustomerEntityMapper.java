package com.francinjr.rentalbusiness.components.customer.infrastructure.adapters.mappers;

import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import com.francinjr.rentalbusiness.commons.infrastructure.persistence.EntityMapper;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.AddressEntity;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.CustomerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerEntityMapper implements EntityMapper<Customer, CustomerEntity> {
    private final AddressEntityMapper addressMapper;
    public CustomerEntity modelToEntity(Customer customer) {
        if (customer == null) return null;

        AddressEntity addressEntity = addressMapper.modelToEntity(customer.getAddress());
        return new CustomerEntity(
                customer.getId(),
                customer.getName(),
                customer.getBirthDate(),
                customer.getEmail(),
                customer.getCpf(),
                addressEntity
        );
    }

    public Customer entityToModel(CustomerEntity entity) {
        if (entity == null) return null;

        Address address = addressMapper.entityToModel(entity.getAddress());
        return new Customer(
                entity.getId(),
                entity.getName(),
                entity.getBirthDate(),
                entity.getEmail(),
                entity.getCpf(),
                address
        );
    }
}
