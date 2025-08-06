package com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname;

public record AddressOutput(String cep, String street, String additionalInfo,
                            String neighborhood,
                            String city,
                            String state, String number) {

}
