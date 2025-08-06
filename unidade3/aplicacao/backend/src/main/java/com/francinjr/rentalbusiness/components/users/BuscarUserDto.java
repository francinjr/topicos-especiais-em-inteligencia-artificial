package com.francinjr.rentalbusiness.components.users;

import lombok.Data;

@Data
public class BuscarUserDto {

    private Long id;
    private String email;
    private UserRole role;
    private Boolean isPasswordChanged;
}
