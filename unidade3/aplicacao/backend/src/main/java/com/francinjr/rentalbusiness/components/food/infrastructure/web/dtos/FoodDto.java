package com.francinjr.rentalbusiness.components.food.infrastructure.web.dtos;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class FoodDto {
    private Long id;
    private String name;
    private List<String> sequenciaAminoacidos;
}
