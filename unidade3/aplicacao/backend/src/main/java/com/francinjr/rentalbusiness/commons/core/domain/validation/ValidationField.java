package com.francinjr.rentalbusiness.commons.core.domain.validation;

public class ValidationField {
    private String name;
    private String message;

    public ValidationField(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public ValidationField() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
