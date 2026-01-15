package org.hotwax.hotwaxassignment.repository;

import org.hotwax.hotwaxassignment.entity.Order_Header;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_HeaderRepository extends JpaRepository<Order_Header, Integer> {
}
