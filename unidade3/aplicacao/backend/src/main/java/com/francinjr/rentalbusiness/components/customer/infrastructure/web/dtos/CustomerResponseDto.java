package com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerResponseDto {

    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String cpf;
    private AddressDto address;
}
