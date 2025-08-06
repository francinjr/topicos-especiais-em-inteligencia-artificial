package com.francinjr.rentalbusiness.components.customer.core.application.usecases.create;

import java.time.LocalDate;

public record CreateCustomerUseCaseInput(String name, LocalDate birthDate, String email,
                                         String cpf, CreateAddressInput address) {

}
