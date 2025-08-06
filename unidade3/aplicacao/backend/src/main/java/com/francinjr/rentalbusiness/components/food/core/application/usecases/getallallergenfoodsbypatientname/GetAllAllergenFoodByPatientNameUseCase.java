package com.francinjr.rentalbusiness.components.food.core.application.usecases.getallallergenfoodsbypatientname;

import java.util.List;

public interface GetAllAllergenFoodByPatientNameUseCase {
    List<String> getAllAllergenFoodsByPatientName(String patientName);
}
