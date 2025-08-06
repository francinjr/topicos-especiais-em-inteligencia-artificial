package com.francinjr.rentalbusiness.commons.infrastructure.adapters;

import com.francinjr.rentalbusiness.commons.core.domain.entities.DomainModel;
import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;

import java.util.List;

public interface UniqueFieldValidatorAdapter<M extends DomainModel> {
    List<ValidationField> validate(M model) throws Exception;
}
