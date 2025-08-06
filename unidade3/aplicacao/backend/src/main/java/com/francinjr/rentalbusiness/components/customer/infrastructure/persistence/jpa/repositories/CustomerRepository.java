package com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.repositories;

import com.francinjr.rentalbusiness.components.customer.infrastructure.persistence.jpa.entities.CustomerEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByName(String name);
    Page<CustomerEntity> findByNameContaining(String name, Pageable pageable);
}
