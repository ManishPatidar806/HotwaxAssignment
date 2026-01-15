package org.hotwax.hotwaxassignment.repository;

import org.hotwax.hotwaxassignment.entity.Customer;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(@NonNull String attr0);
}
