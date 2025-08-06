package com.francinjr.rentalbusiness.components.customer.core.application.usecases.create;

public record CreateAddressInput(String cep, String street, String additionalInfo,
                                 String neighborhood,
                                 String city,
                                 String state, String number) {

}