package com.francinjr.rentalbusiness.components.customer.core.application.usecases.create;

public record CreateAddressOutput(Long id, String cep, String street, String additionalInfo,
                                 String neighborhood,
                                 String city,
                                 String state, String number) {

}
