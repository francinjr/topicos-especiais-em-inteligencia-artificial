package com.francinjr.rentalbusiness.components.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRole(UserRole role);
    boolean existsById(Long id);
    boolean existsByEmail(String email);
    User findByEmail(String email);

}
