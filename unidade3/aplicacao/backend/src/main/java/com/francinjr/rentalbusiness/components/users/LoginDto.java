package com.francinjr.rentalbusiness.components.users;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Você deve inserir o seu e-mail.")
    private String email;
    @NotBlank(message = "Você deve inserir sua senha.")
    private String password;
    //TODO: Tentar adicionar o User Admin
}
