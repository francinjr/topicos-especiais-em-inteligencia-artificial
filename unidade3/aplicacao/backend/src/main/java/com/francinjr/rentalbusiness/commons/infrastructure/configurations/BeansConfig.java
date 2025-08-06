package com.francinjr.rentalbusiness.commons.infrastructure.configurations;

import com.francinjr.rentalbusiness.commons.infrastructure.adapters.UniqueFieldValidatorAdapter;
import com.francinjr.rentalbusiness.components.customer.core.application.mappers.AddressDomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.application.mappers.CustomerDomainMapper;
import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.delete.DeleteCustomerUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.delete.DeleteCustomerUseCaseImpl;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.ListCustomerByNameUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.ListCustomerByNameUseCaseImpl;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCaseImpl;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCaseImpl;
import com.francinjr.rentalbusiness.components.food.core.application.gateways.FoodRepositoryGateway;
import com.francinjr.rentalbusiness.components.food.core.application.usecases.getallallergenfoodsbypatientname.GetAllAllergenFoodByPatientNameUseCase;
import com.francinjr.rentalbusiness.components.food.core.application.usecases.getallallergenfoodsbypatientname.GetAllAllergenFoodByPatientNameUseCaseImpl;
import com.francinjr.rentalbusiness.components.food.core.application.usecases.getallnonallergenfoodsbypatientname.GetAllNonAllergenFoodByPatientNameUseCase;
import com.francinjr.rentalbusiness.components.food.core.application.usecases.getallnonallergenfoodsbypatientname.GetAllNonAllergenFoodByPatientNameUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public CreateCustomerUseCase createCustomerUseCase(
            CustomerRepositoryGateway customerRepositoryGateway,
            UniqueFieldValidatorAdapter<Customer> uniqueFieldsValidator,
            CustomerDomainMapper customerDomainMapper) {
        return new CreateCustomerUseCaseImpl(customerRepositoryGateway, uniqueFieldsValidator,
                customerDomainMapper);
    }

    @Bean
    public AddressDomainMapper addressDomainMapper() {
        return new AddressDomainMapper();
    }

    @Bean
    public CustomerDomainMapper customerDomainMapper(AddressDomainMapper addressDomainMapper) {
        return new CustomerDomainMapper(addressDomainMapper);
    }

    @Bean
    public UpdateCustomerUseCase updateCustomerUseCase(
            CustomerRepositoryGateway customerRepositoryGateway,
            UniqueFieldValidatorAdapter<Customer> uniqueFieldsValidator,
            CustomerDomainMapper customerDomainMapper) {
        return new UpdateCustomerUseCaseImpl(customerRepositoryGateway, uniqueFieldsValidator,
                customerDomainMapper);
    }

    @Bean
    public ListCustomerByNameUseCase listCustomerByNameUseCase(
            CustomerRepositoryGateway customerRepositoryGateway,
            CustomerDomainMapper customerDomainMapper) {
        return new ListCustomerByNameUseCaseImpl(customerRepositoryGateway, customerDomainMapper);
    }

    @Bean
    public DeleteCustomerUseCase deleteCustomerUseCase(
            CustomerRepositoryGateway customerRepositoryGateway) {
        return new DeleteCustomerUseCaseImpl(customerRepositoryGateway);
    }

    @Bean
    public GetAllAllergenFoodByPatientNameUseCase getAllergenFoodByPatientNameUseCase(
            FoodRepositoryGateway foodRepositoryGateway) {
        return new GetAllAllergenFoodByPatientNameUseCaseImpl(foodRepositoryGateway);
    }

    @Bean
    public GetAllNonAllergenFoodByPatientNameUseCase getAllNonAllergenFoodByPatientNameUseCase(
            FoodRepositoryGateway foodRepositoryGateway) {
        return new GetAllNonAllergenFoodByPatientNameUseCaseImpl(foodRepositoryGateway);
    }
}
