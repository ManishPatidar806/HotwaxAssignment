package org.hotwax.hotwaxassignment.repository;

import org.hotwax.hotwaxassignment.entity.Order_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Order_ItemRepository extends JpaRepository<Order_Item, Integer> {
}
