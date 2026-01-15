package org.hotwax.hotwaxassignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @Column(nullable = false,length = 100)
    private String product_name;
    @Column(length = 30)
    private  String color;
    @Column(length = 10)
    private String size;


}
