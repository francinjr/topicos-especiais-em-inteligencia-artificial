package com.francinjr.rentalbusiness.components.customer.core.application.usecases.findall;

import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;
import com.francinjr.rentalbusiness.components.customer.core.application.mappers.CustomerDomainMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllCustomersNameUseCase implements ListAllCustomersNameUseCase {
    private final CustomerRepositoryGateway customerRepositoryGateway;

    @Override
    public List<String> execute() {
        return customerRepositoryGateway.findAllCustomersName();
    }
}
