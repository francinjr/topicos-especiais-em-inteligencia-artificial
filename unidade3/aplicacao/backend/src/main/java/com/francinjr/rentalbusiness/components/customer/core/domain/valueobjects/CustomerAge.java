package com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects;

import com.francinjr.rentalbusiness.commons.core.exceptions.DomainException;
import java.time.LocalDate;
import java.time.Period;

public class CustomerAge {

    private LocalDate birthDate;
    private Integer value;

    public CustomerAge(LocalDate birthDate) {
        validateAge(birthDate);

        this.birthDate = birthDate;
        this.value = calculateAge(birthDate);
    }

    public void validateAge(LocalDate birthDate) {
        if (birthDate == null) {
            throw new DomainException("A data de nascimento deve ser informada.");
        }
    }

    public Integer calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Integer getValue() {
        return value;
    }
}
