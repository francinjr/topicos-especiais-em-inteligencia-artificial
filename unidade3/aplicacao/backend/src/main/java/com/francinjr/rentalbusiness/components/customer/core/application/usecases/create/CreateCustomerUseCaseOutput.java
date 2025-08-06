package com.francinjr.rentalbusiness.components.customer.core.application.usecases.create;

import java.time.LocalDate;

public record CreateCustomerUseCaseOutput(Long id, String name, LocalDate birthDate, String email,
                                          String cpf, CreateAddressOutput address) {

}
