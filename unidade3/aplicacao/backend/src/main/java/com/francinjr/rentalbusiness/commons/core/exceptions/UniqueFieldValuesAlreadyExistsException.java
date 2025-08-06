package com.francinjr.rentalbusiness.commons.core.exceptions;

import com.francinjr.rentalbusiness.commons.core.domain.validation.ValidationField;

import java.util.List;

public class UniqueFieldValuesAlreadyExistsException extends RuntimeException {
    private List<ValidationField> existingFieldValues;

    public UniqueFieldValuesAlreadyExistsException(
            String message, List<ValidationField> existingFieldValues) {
        super(message);
        this.existingFieldValues = existingFieldValues;
    }

    public UniqueFieldValuesAlreadyExistsException(List<ValidationField> existingFieldValues) {
        super("Há campos com valores que já estão em uso");
        this.existingFieldValues = existingFieldValues;
    }

    public List<ValidationField> getExistingFieldValues() {
        return existingFieldValues;
    }

    public void setExistingFieldValues(List<ValidationField> existingFieldValues) {
        this.existingFieldValues = existingFieldValues;
    }
}
