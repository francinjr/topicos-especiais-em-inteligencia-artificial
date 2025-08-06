package com.francinjr.rentalbusiness.components.food.core.domain.entities;

import com.francinjr.rentalbusiness.commons.core.domain.entities.DomainModel;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;
import com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects.Cpf;
import com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects.CustomerAge;
import com.francinjr.rentalbusiness.commons.core.exceptions.DomainException;
import com.francinjr.rentalbusiness.components.customer.core.domain.valueobjects.Email;
import java.time.LocalDate;

public class Food implements DomainModel {

    private final Long id;
    private String name;
    private String sequenciaAminoAcidos;

    public Food(Long id, String name, String sequenciaAminoAcidos) {
        this.id = id;
        this.name = name;
        this.sequenciaAminoAcidos = sequenciaAminoAcidos;
    }
}
