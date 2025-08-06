package com.francinjr.rentalbusiness.components.customer.infrastructure.adapters.validators;

import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import com.francinjr.rentalbusiness.components.customer.infrastructure.adapters.mappers.CustomerEntityMapper;
import com.francinjr.rentalbusiness.commons.infrastructure.adapters.UniqueFieldsValidatorAdapterImpl;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.CustomerEntity;
import com.francinjr.rentalbusiness.commons.infrastructure.validators.UniqueFieldsValidatorImpl;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CustomerUniqueFieldsValidatorAdapter
        extends UniqueFieldsValidatorAdapterImpl<Customer, CustomerEntity> {

    public CustomerUniqueFieldsValidatorAdapter(UniqueFieldsValidatorImpl validator,
            CustomerEntityMapper mapper) {
        super(validator, mapper);
    }

    public List<ValidationField> validate(Customer model) throws Exception {
        return super.validate(model);
    }
}
