package org.hotwax.hotwaxassignment.repository;

import org.hotwax.hotwaxassignment.entity.Contact_Mech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Contact_MechRepository extends JpaRepository<Contact_Mech, Integer> {
}
