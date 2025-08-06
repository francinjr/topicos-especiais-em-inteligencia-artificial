package com.francinjr.rentalbusiness.commons.infrastructure.validators;

import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;

import java.util.List;

public interface UniqueFieldsValidator {
    List<ValidationField> validate(Object entityObject) throws Exception;
}
