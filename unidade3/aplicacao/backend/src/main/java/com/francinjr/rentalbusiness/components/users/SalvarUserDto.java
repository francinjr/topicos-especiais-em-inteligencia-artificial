package com.francinjr.rentalbusiness.components.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SalvarUserDto {

    @NotBlank(message = "Digite o email.")
    @Email(message = "O email precisa estar no formato: exemplo@email.com")
    private String email;

    @NotNull(message = "Selecione o tipo de usuário.")
    private UserRole role;
}
