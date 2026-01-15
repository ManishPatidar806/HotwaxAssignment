package org.hotwax.hotwaxassignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Contact_Mech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int contact_mech_id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false, length = 100)
    private String street_address;
    @Column(nullable = false, length = 50)
    private String city;
    @Column(nullable = false, length = 50)
    private String state;
    @Column(nullable = false, length = 20)
    private String postal_code;
    @Column(length = 20)
    private String phone_number;
    @Column(length = 100)
    private String email;

}
