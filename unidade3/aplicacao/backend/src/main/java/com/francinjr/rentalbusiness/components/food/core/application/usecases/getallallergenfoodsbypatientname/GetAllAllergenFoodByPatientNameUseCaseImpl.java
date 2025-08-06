package com.francinjr.rentalbusiness.components.food.core.application.usecases.getallallergenfoodsbypatientname;

import com.francinjr.rentalbusiness.components.food.core.application.gateways.FoodRepositoryGateway;
import java.util.List;

public class GetAllAllergenFoodByPatientNameUseCaseImpl implements
        GetAllAllergenFoodByPatientNameUseCase {
    private final FoodRepositoryGateway foodRepositoryGateway;

    public GetAllAllergenFoodByPatientNameUseCaseImpl(FoodRepositoryGateway foodRepositoryGateway) {
        this.foodRepositoryGateway = foodRepositoryGateway;
    }

    @Override
    public List<String> getAllAllergenFoodsByPatientName(String patientName) {
        return foodRepositoryGateway.findAllAllergenFoodsByPatientName(patientName);
    }
}

