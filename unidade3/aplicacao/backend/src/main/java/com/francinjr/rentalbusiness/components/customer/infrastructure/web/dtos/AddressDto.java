package com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddressDto {

    private String cep;
    private String street;
    private String additionalInfo;
    private String neighborhood;
    private String city;
    private String state;
    private String number;
}
