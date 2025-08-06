package com.francinjr.rentalbusiness.components.food.infrastructure.adapters.gateways;

import com.francinjr.rentalbusiness.components.food.core.application.gateways.FoodRepositoryGateway;
import com.francinjr.rentalbusiness.components.food.infrastructure.persistence.apachejena.FoodJenaRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class FoodRepositoryGatewayImpl implements FoodRepositoryGateway {
    private final FoodJenaRepository customerRepository;

    @Override
    public List<String> findAllAllergenFoodsByPatientName(String patientName) {
        return customerRepository.findAllAllergenFoodsByPatientName(patientName);
    }

    @Override
    public List<String> findAllNonAllergenFoodsByPatientName(String patientName) {
        return customerRepository.findAllNonAllergenFoodsByPatientName(patientName);
    }

    @Override
    public List<String> findAllFoods() {
        return customerRepository.findAllFoods();
    }

    /*@Transactional
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
    }*/
}
