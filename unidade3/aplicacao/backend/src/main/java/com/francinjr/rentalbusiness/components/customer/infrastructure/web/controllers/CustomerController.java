package com.francinjr.rentalbusiness.components.customer.infrastructure.web.controllers;

import com.francinjr.rentalbusiness.commons.core.domain.entities.PaginationResult;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCaseInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateCustomerUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.delete.DeleteCustomerUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.findall.FindAllCustomersNameUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.CustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.ListCustomerByNameUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCase;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCaseInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateCustomerUseCaseOutput;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.CustomerResponseDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.CreateCustomerRequestDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.CustomerSimpleResponseDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.UpdateCustomerRequestDto;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.mappers.CustomerWebMapper;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ApiSuccessResponse;
import com.francinjr.rentalbusiness.commons.infrastructure.web.response.ResponseUtil;
import com.francinjr.rentalbusiness.commons.infrastructure.validators.RequestDtoClassTypeContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerWebMapper mapper;
    private final RequestDtoClassTypeContext requestClassTypeContext;
    private final CreateCustomerUseCase createUseCase;
    private final UpdateCustomerUseCase updateUseCase;
    private final ListCustomerByNameUseCase listCustomerByNameUseCase;
    private final DeleteCustomerUseCase deleteUseCase;
    private final FindAllCustomersNameUseCase findAllCustomersNameUseCase;


    @PostMapping
    public ResponseEntity<ApiSuccessResponse<CustomerResponseDto>> create(
            @Valid @RequestBody CreateCustomerRequestDto saveDto, HttpServletRequest request)
            throws Exception {
        requestClassTypeContext.setDtoClass(saveDto.getClass());

        CreateCustomerUseCaseInput input = mapper.requestCreateDtoToCreateUseCaseInput(saveDto);
        CreateCustomerUseCaseOutput output = createUseCase.execute(input);
        CustomerResponseDto readDto = mapper.createUseCaseOutputToResponseDto(output);

        ApiSuccessResponse<CustomerResponseDto> response = ResponseUtil.success(readDto,
                "Cliente criado com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<CustomerResponseDto>> update(
            @Valid @RequestBody UpdateCustomerRequestDto saveDto, @PathVariable Long id,
            HttpServletRequest request) throws Exception {
        requestClassTypeContext.setDtoClass(saveDto.getClass());

        UpdateCustomerUseCaseInput input = mapper.requestUpdateDtoToUpdateUseCaseInput(saveDto);
        UpdateCustomerUseCaseOutput output = updateUseCase.execute(input, id);
        CustomerResponseDto readDto = mapper.updateUseCaseOutputToResponseDto(output);

        ApiSuccessResponse<CustomerResponseDto> response = ResponseUtil.success(readDto,
                "Cliente criado com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiSuccessResponse<Page<CustomerResponseDto>>> getAllByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {

        PaginationResult<CustomerUseCaseOutput> output = listCustomerByNameUseCase.execute(name,
                page, size);

        List<CustomerResponseDto> dtos = output.getContent().stream()
                .map(mapper::useCaseOutputToResponseDto)
                .toList();

        Page<CustomerResponseDto> readDtosPage = new PageImpl<>(
                dtos,
                PageRequest.of(page, size),
                output.getTotalElements()
        );

        ApiSuccessResponse<Page<CustomerResponseDto>> response = ResponseUtil.success(readDtosPage,
                "Clientes listados com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSuccessResponse<Void>> delete(@PathVariable Long id,
            HttpServletRequest request) throws Exception {
        deleteUseCase.execute(id);

        ApiSuccessResponse<Void> response = ResponseUtil.success(null,
                "Cliente deletado com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<ApiSuccessResponse<List<CustomerSimpleResponseDto>>> getAllCustomersNames(HttpServletRequest request) {

        List<String> patientsNames = findAllCustomersNameUseCase.execute();
        List<CustomerSimpleResponseDto> customerSimpleResponseDto = mapper.useCaseOutputStringToSimpleResponseDto(patientsNames);

        ApiSuccessResponse<List<CustomerSimpleResponseDto>> response = ResponseUtil.success(customerSimpleResponseDto,
                "Clientes listados com sucesso.", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
