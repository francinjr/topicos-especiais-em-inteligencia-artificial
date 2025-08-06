package com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects;

import com.francinjr.rentalbusiness.commons.core.exceptions.DomainException;
import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private String value;

    private static final int MIN_SIZE = 6;
    private static final int MAX_SIZE = 254;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[\\p{L}0-9_+&*-]+(?:\\.[\\p{L}0-9_+&*-]+)*@" +
                    "(?:[\\p{L}0-9-]+\\.)+[\\p{L}]{2,}$"
    );

    public Email(String rawEmailValue) {
        if (rawEmailValue == null) {
            throw new DomainException("O email não pode ser nulo.");
        }

        String processedEmail = process(rawEmailValue);
        validate(processedEmail);
        this.value = processedEmail;
    }

    private String process(String rawEmailValue) {
        return rawEmailValue.trim();
    }

    private void validate(String processedEmailValue) {
        if (processedEmailValue.isEmpty()) {
            throw new DomainException("O email não pode ser vazio.");
        }

        if (processedEmailValue.length() < MIN_SIZE) {
            throw new DomainException(
                    "O email deve ter pelo menos " + MIN_SIZE + " caracteres. Fornecido: '"
                            + processedEmailValue + "' (" + processedEmailValue.length() + ")");
        }

        if (processedEmailValue.length() > MAX_SIZE) {
            throw new DomainException(
                    "O email deve ter no máximo " + MAX_SIZE + " caracteres. Fornecido: '"
                            + processedEmailValue + "' (" + processedEmailValue.length() + ")");
        }

        if (!EMAIL_PATTERN.matcher(processedEmailValue).matches()) {
            throw new DomainException(
                    "O formato do email é inválido: '" + processedEmailValue + "'");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}