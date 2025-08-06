package com.francinjr.rentalbusiness.components.food.infrastructure.web.controllers;

import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ApiSuccessResponse;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ResponseUtil;
import com.francinjr.rentalbusiness.components.food.core.application.gateways.FoodRepositoryGateway;
import com.francinjr.rentalbusiness.components.food.core.application.usecases.getallallergenfoodsbypatientname.GetAllAllergenFoodByPatientNameUseCase;
import com.francinjr.rentalbusiness.components.food.core.application.usecases.getallnonallergenfoodsbypatientname.GetAllNonAllergenFoodByPatientNameUseCase;
import com.francinjr.rentalbusiness.components.food.infrastructure.web.dtos.FoodDto;
import com.francinjr.rentalbusiness.components.food.infrastructure.web.mappers.FoodWebMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/foods")
public class FoodController {
    private final GetAllAllergenFoodByPatientNameUseCase getAllAllergenFoodByPatientNameUseCase;
    private final GetAllNonAllergenFoodByPatientNameUseCase getAllNonAllergenFoodByPatientNameUseCase;
    private final FoodWebMapper foodWebMapper;
    private final FoodRepositoryGateway foodRepositoryGateway;

    @GetMapping("/allergensbypatientname")
    public ResponseEntity<ApiSuccessResponse<List<FoodDto>>> getAllAllergenFoodByPatientName(
            @RequestParam String patientName,
            HttpServletRequest request) throws Exception {

        List<String> allergenFoods = getAllAllergenFoodByPatientNameUseCase.getAllAllergenFoodsByPatientName(patientName);
        List<FoodDto> allergenFoodsDto = foodWebMapper.getFoodUseCaseOutputToFoodDto(allergenFoods);
        ApiSuccessResponse<List<FoodDto>> response = ResponseUtil.success(allergenFoodsDto,
                "Alimentos alergênicos para o paciente buscados com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/nonallergensbypatientname")
    public ResponseEntity<ApiSuccessResponse<List<FoodDto>>> getAllNonAllergenFoodByPatientName(
            @RequestParam String patientName,
            HttpServletRequest request) throws Exception {

        List<String> nonAllergenFoods = getAllNonAllergenFoodByPatientNameUseCase.getAllNonAllergenFoodsByPatientName(patientName);
        List<FoodDto> allergenFoodsDto = foodWebMapper.getFoodUseCaseOutputToFoodDto(nonAllergenFoods);
        ApiSuccessResponse<List<FoodDto>> response = ResponseUtil.success(allergenFoodsDto,
                "Alimentos não alergênicos para o paciente buscados com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiSuccessResponse<List<FoodDto>>> getAll(
            HttpServletRequest request) throws Exception {

        List<String> allFoods = foodRepositoryGateway.findAllFoods();
        List<FoodDto> allergenFoodsDto = foodWebMapper.getFoodUseCaseOutputToFoodDto(allFoods);
        ApiSuccessResponse<List<FoodDto>> response = ResponseUtil.success(allergenFoodsDto,
                "Alimentos buscados com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
