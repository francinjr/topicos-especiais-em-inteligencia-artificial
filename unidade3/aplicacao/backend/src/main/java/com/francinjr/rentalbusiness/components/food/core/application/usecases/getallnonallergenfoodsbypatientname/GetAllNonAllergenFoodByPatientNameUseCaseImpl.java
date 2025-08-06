package com.francinjr.rentalbusiness.components.food.core.application.usecases.getallnonallergenfoodsbypatientname;

import com.francinjr.rentalbusiness.components.food.core.application.gateways.FoodRepositoryGateway;
import java.util.List;

public class GetAllNonAllergenFoodByPatientNameUseCaseImpl implements
        GetAllNonAllergenFoodByPatientNameUseCase {
    private final FoodRepositoryGateway foodRepositoryGateway;

    public GetAllNonAllergenFoodByPatientNameUseCaseImpl(FoodRepositoryGateway foodRepositoryGateway) {
        this.foodRepositoryGateway = foodRepositoryGateway;
    }

    @Override
    public List<String> getAllNonAllergenFoodsByPatientName(String patientName) {
        return foodRepositoryGateway.findAllNonAllergenFoodsByPatientName(patientName);
    }
}
