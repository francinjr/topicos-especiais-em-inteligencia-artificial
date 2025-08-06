package com.francinjr.rentalbusiness.components.customer.core.application.usecases.update;

import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressInput;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Address;
import java.time.LocalDate;

public record UpdateCustomerUseCaseInput(String name, LocalDate birthDate, String email,
                                         UpdateAddressInput address) {

}
