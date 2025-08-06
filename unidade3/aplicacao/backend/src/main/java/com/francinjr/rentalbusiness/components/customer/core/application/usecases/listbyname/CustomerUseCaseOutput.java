package com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname;

import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressOutput;
import java.time.LocalDate;

public record CustomerUseCaseOutput(Long id, String name, LocalDate birthDate, String email,
                                    String cpf, AddressOutput address) {

}
