package com.francinjr.rentalbusiness.commons.infrastructure.adapters;

import com.francinjr.rentalbusiness.commons.core.domain.entities.DomainModel;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;
import com.francinjr.rentalbusiness.commons.infrastructure.persistence.EntityMapper;
import com.francinjr.rentalbusiness.commons.infrastructure.persistence.EntityModel;
import com.francinjr.rentalbusiness.commons.infrastructure.validators.UniqueFieldsValidator;

import java.util.List;

public abstract class UniqueFieldsValidatorAdapterImpl<
        M extends DomainModel,
        E extends EntityModel> implements UniqueFieldValidatorAdapter<M> {

    private final UniqueFieldsValidator validator;
    private final EntityMapper<M, E> mapper;

    public UniqueFieldsValidatorAdapterImpl(UniqueFieldsValidator validator, EntityMapper<M, E> mapper) {
        this.validator = validator;
        this.mapper = mapper;
    }

    public List<ValidationField> validate(M model) throws Exception {
        E entity = mapper.modelToEntity(model);
        return validator.validate(entity);
    }
}

