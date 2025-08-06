package com.francinjr.rentalbusiness.components.food.infrastructure.web.mappers;

import com.francinjr.rentalbusiness.components.food.infrastructure.web.dtos.FoodDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class FoodWebMapper {

    public List<FoodDto> getFoodUseCaseOutputToFoodDto(List<String> foods) {
        List<FoodDto> foodsDto = new ArrayList<>();
        long i = 1;
        for (String food : foods) {
            foodsDto.add(new FoodDto(i, food, null));
            i++;
        }

        return foodsDto;
    }
}
