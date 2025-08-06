package com.francinjr.rentalbusiness.components.customer.core.application.usecases.update;

public record UpdateAddressInput(String cep, String street, String additionalInfo,
                                 String neighborhood,
                                 String city,
                                 String state, String number) {

}
