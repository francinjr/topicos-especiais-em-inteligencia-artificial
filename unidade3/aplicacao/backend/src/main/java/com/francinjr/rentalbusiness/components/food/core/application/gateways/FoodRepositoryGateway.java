package com.francinjr.rentalbusiness.components.food.core.application.gateways;

import com.francinjr.rentalbusiness.commons.core.domain.entities.PaginationResult;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import java.util.List;
import java.util.Optional;

public interface FoodRepositoryGateway {
    List<String> findAllAllergenFoodsByPatientName(String patientName);
    List<String> findAllNonAllergenFoodsByPatientName(String patientName);
    List<String> findAllFoods();
}
