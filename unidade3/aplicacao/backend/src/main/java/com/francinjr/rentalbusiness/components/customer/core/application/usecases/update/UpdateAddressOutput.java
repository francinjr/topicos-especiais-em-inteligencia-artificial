package com.francinjr.rentalbusiness.components.customer.core.application.usecases.update;

public record UpdateAddressOutput(Long id, String cep, String street, String additionalInfo,
                                  String neighborhood,
                                  String city,
                                  String state, String number) {

}
