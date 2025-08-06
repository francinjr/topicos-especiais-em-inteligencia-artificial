package com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos;

import com.francinjr.rentalbusiness.commons.core.domain.validation.UniqueField;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.CustomerEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CreateCustomerRequestDto {

    @NotBlank(message = "O nome deve ser informado.")
    @UniqueField(message = "Já existe um cliente cadastrado com esse nome.", fieldName = "name", entityClass = CustomerEntity.class)
    private String name;

    @NotNull(message = "A data de nascimento deve ser informada.")
    @Past(message = "A data de nascimento deve ser no passado.")
    private LocalDate birthDate;

    @NotBlank(message = "O e-mail deve ser informado.")
    @Email(message = "Formato de e-mail inválido.")
    @UniqueField(message = "Já existe um cliente cadastrado com esse email.", fieldName = "email", entityClass = CustomerEntity.class)
    private String email;

    @NotBlank(message = "O CPF deve ser informado.")
    @Pattern(
            regexp = "\\d{11}",
            message = "O CPF deve conter exatamente 11 dígitos numéricos."
    )
    @UniqueField(message = "Já existe um cliente cadastrado com esse cpf.", fieldName = "cpf", entityClass = CustomerEntity.class)
    private String cpf;

    @NotNull(message = "O endereço deve ser informado.")
    private AddressDto address;
}
