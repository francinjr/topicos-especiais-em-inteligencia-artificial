package com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects;

import com.francinjr.rentalbusiness.commons.core.exceptions.DomainException;

import java.util.InputMismatchException;

public class Cpf {
    private final String value;

    public Cpf(String value) {
        String normalizedValue = normalize(value);
        validate(normalizedValue);

        this.value = normalizedValue;
    }

    private String normalize(String value) {
        if (value == null || value.isEmpty()) {
            throw new DomainException("CPF precisa ser informado.");
        }
        return value.replaceAll("[^\\d]", "");
    }

    private void validate(String value) {
        if (!isValidCpf(value)) {
            throw new DomainException("O CPF informado não é inválido.");
        }
    }

    private boolean isValidCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            return false;
        }

        if (cpf.equals("00000000000") || cpf.equals("11111111111") || cpf.equals("22222222222") ||
                cpf.equals("33333333333") || cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") || cpf.equals("88888888888") ||
                cpf.equals("99999999999")) {
            return false;
        }

        try {
            char dig10, dig11;
            int sum, i, remainder, num, weight;

            sum = 0;
            weight = 10;
            for (i = 0; i < 9; i++) {
                num = cpf.charAt(i) - '0';
                sum += num * weight;
                weight--;
            }

            remainder = 11 - (sum % 11);
            dig10 = (remainder == 10 || remainder == 11) ? '0' : (char)(remainder + '0');

            sum = 0;
            weight = 11;
            for (i = 0; i < 10; i++) {
                num = cpf.charAt(i) - '0';
                sum += num * weight;
                weight--;
            }

            remainder = 11 - (sum % 11);
            dig11 = (remainder == 10 || remainder == 11) ? '0' : (char)(remainder + '0');

            return dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10);
        } catch (InputMismatchException e) {
            return false;
        }
    }

    public String getValue() {
        return value;
    }

    public String getFormattedValue() {
        return String.format("%s.%s.%s-%s",
                value.substring(0, 3),
                value.substring(3, 6),
                value.substring(6, 9),
                value.substring(9, 11));
    }

    @Override
    public String toString() {
        return getFormattedValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cpf)) return false;
        Cpf other = (Cpf) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
