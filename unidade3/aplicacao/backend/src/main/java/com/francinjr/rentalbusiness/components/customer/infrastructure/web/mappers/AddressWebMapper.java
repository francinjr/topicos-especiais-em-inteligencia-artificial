package com.francinjr.rentalbusiness.components.customer.infrastructure.web.mappers;

import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.create.CreateAddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.listbyname.AddressOutput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateAddressInput;
import com.francinjr.rentalbusiness.components.customer.core.application.usecases.update.UpdateAddressOutput;
import com.francinjr.rentalbusiness.components.customer.infrastructure.web.dtos.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressWebMapper {

    public CreateAddressInput requestDtoToCreateUseCaseInput(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        return new CreateAddressInput(
                dto.getCep(),
                dto.getStreet(),
                dto.getAdditionalInfo(),
                dto.getNeighborhood(),
                dto.getCity(),
                dto.getState(),
                dto.getNumber()
        );
    }

    public AddressDto createUseCaseOutputToWebDto(CreateAddressOutput output) {
        return new AddressDto(output.cep(),
                output.street(),
                output.additionalInfo(),
                output.neighborhood(),
                output.city(),
                output.state(),
                output.number());
    }


    public UpdateAddressInput requestDtoToUpdateUseCaseInput(AddressDto dto) {
        if (dto == null) {
            return null;
        }

        return new UpdateAddressInput(
                dto.getCep(),
                dto.getStreet(),
                dto.getAdditionalInfo(),
                dto.getNeighborhood(),
                dto.getCity(),
                dto.getState(),
                dto.getNumber()
        );
    }


    public AddressDto updateUseCaseOutputToWebDto(UpdateAddressOutput output) {
        return new AddressDto(output.cep(),
                output.street(),
                output.additionalInfo(),
                output.neighborhood(),
                output.city(),
                output.state(),
                output.number());
    }

    public AddressDto useCaseOutputToWebDto(AddressOutput output) {
        return new AddressDto(output.cep(),
                output.street(),
                output.additionalInfo(),
                output.neighborhood(),
                output.city(),
                output.state(),
                output.number());
    }


}
