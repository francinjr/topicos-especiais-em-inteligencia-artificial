package com.francinjr.rentalbusiness.components.customer.infrastructure.adapters.gateways;

import com.francinjr.rentalbusiness.components.customer.core.application.gateways.CustomerRepositoryGateway;
import com.francinjr.rentalbusiness.commons.core.domain.entities.PaginationResult;
import com.francinjr.rentalbusiness.components.customer.core.domain.entities.Customer;
import com.francinjr.rentalbusiness.components.customer.infrastructure.adapters.mappers.CustomerEntityMapper;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.apachejena.CustomerJenaRepository;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.CustomerEntity;
import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.repositories.CustomerRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CustomerRepositoryGatewayImpl implements CustomerRepositoryGateway {
    private final CustomerRepository customerRepository;
    private final CustomerEntityMapper mapper;
    private final CustomerJenaRepository customerJenaRepository;

    @Transactional
    @Override
    public Customer create(Customer customer) {
        CustomerEntity entity = mapper.modelToEntity(customer);
        CustomerEntity createdEntity = customerRepository.save(entity);

        return mapper.entityToModel(createdEntity);
    }

    @Transactional
    @Override
    public Customer update(Customer customer) {
        CustomerEntity entity = mapper.modelToEntity(customer);
        CustomerEntity updatedEntity = customerRepository.save(entity);

        return mapper.entityToModel(updatedEntity);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        Optional<CustomerEntity> optionalEntity = customerRepository.findById(id);

        if (optionalEntity.isPresent()) {
            CustomerEntity entity = optionalEntity.get();
            Customer model = mapper.entityToModel(entity);

            return Optional.of(model);
        }
        return Optional.empty();
    }


    @Override
    public Optional<Customer> findByName(String name) {
        return customerRepository.findByName(name)
                .map(mapper::entityToModel);
    }

    @Override
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public PaginationResult<Customer> findAllByName(String name, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        Page<CustomerEntity> pageResult = customerRepository.findByNameContaining(name, pageable);

        List<Customer> models = pageResult.getContent().stream()
                .map(mapper::entityToModel)
                .toList();

        return new PaginationResult<>(models, pageResult.getTotalElements());
    }

    @Override
    public List<String> findAllCustomersName() {
        return customerJenaRepository.findAllPatients();
    }
}
