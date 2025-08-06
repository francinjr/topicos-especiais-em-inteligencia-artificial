package com.francinjr.rentalbusiness.components.customer.infrastructure.web.mappers;

import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCaseInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.CustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateAddressInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCaseInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.AddressDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.CreateCustomerRequestDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.CustomerResponseDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.CustomerSimpleResponseDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.UpdateCustomerRequestDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CustomerWebMapper {

    private final AddressWebMapper addressDtoMapper;

    public CreateCustomerUseCaseInput requestCreateDtoToCreateUseCaseInput(
            CreateCustomerRequestDto requestDto) {
        CreateAddressInput addressInput = addressDtoMapper.requestDtoToCreateUseCaseInput(
                requestDto.getAddress());

        return new CreateCustomerUseCaseInput(requestDto.getName(), requestDto.getBirthDate(),
                requestDto.getEmail(),
                requestDto.getCpf(), addressInput);
    }

    public CustomerResponseDto createUseCaseOutputToResponseDto(
            CreateCustomerUseCaseOutput output) {
        AddressDto addressDto = addressDtoMapper.createUseCaseOutputToWebDto(output.address());
        return new CustomerResponseDto(output.id(),
                output.name(),
                output.birthDate(),
                output.email(),
                output.cpf(),
                addressDto);
    }

    public UpdateCustomerUseCaseInput requestUpdateDtoToUpdateUseCaseInput(
            UpdateCustomerRequestDto requestDto) {
        UpdateAddressInput addressInput = addressDtoMapper.requestDtoToUpdateUseCaseInput(
                requestDto.getAddress());

        return new UpdateCustomerUseCaseInput(requestDto.getName(), requestDto.getBirthDate(),
                requestDto.getEmail(), addressInput);
    }

    public CustomerResponseDto updateUseCaseOutputToResponseDto(
            UpdateCustomerUseCaseOutput output) {
        AddressDto addressDto = addressDtoMapper.updateUseCaseOutputToWebDto(output.address());
        return new CustomerResponseDto(output.id(),
                output.name(),
                output.birthDate(),
                output.email(),
                output.cpf(),
                addressDto);
    }

    public CustomerResponseDto useCaseOutputToResponseDto(
            CustomerUseCaseOutput output) {
        AddressDto addressDto = addressDtoMapper.useCaseOutputToWebDto(output.address());
        return new CustomerResponseDto(output.id(),
                output.name(),
                output.birthDate(),
                output.email(),
                output.cpf(),
                addressDto);
    }

    public List<CustomerSimpleResponseDto> useCaseOutputStringToSimpleResponseDto(List<String> patientsNames) {
        List<CustomerSimpleResponseDto> simpleResponseDtos = new ArrayList<>();
        long i = 1;
        for(String patientName : patientsNames) {
            CustomerSimpleResponseDto simplePatientInfo = new CustomerSimpleResponseDto(i, patientName);
            simpleResponseDtos.add(simplePatientInfo);
            i++;
        }

        return simpleResponseDtos;
    }
}

