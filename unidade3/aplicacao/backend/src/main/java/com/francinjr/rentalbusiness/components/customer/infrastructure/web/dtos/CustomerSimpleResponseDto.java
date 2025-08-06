package com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomerSimpleResponseDto {
    private Long id;
    private String name;
}
