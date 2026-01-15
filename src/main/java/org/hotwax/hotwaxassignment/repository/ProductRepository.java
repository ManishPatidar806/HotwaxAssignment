package org.hotwax.hotwaxassignment.repository;

import org.hotwax.hotwaxassignment.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
