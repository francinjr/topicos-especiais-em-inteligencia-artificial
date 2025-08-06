package com.francinjr.rentalbusiness.components.food.core.application.usecases.getallnonallergenfoodsbypatientname;

import java.util.List;

public interface GetAllNonAllergenFoodByPatientNameUseCase {
    List<String> getAllNonAllergenFoodsByPatientName(String patientName);
}
