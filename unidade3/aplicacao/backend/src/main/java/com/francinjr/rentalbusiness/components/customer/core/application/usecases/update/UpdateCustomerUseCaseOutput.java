package com.francinjr.rentalbusiness.components.customer.core.application.usecases.update;

import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressOutput;
import java.time.LocalDate;

public record UpdateCustomerUseCaseOutput(Long id, String name, LocalDate birthDate, String email,
                                          String cpf, UpdateAddressOutput address) {

}
