package org.hotwax.hotwaxassignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Order_Header {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_id;

    @Column(nullable = false)
    private Date order_date;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "shipping_contact_mech_id", referencedColumnName = "contact_mech_id", nullable = false)
    private Contact_Mech shippingContactMech;

    @ManyToOne
    @JoinColumn(name = "billing_contact_mech_id", referencedColumnName = "contact_mech_id", nullable = false)
    private Contact_Mech billingContactMech;

    @OneToMany(mappedBy = "orderHeader", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order_Item> orderItems = new ArrayList<>();
}
