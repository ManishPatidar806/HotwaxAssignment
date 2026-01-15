package org.hotwax.hotwaxassignment.entity;




import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customer_id;

    @Column(nullable = false,length = 50)
    private String first_name;
    @Column(nullable = false,length = 50)
    private String last_name;
    @Column(nullable = false,unique = true,length = 100)
    private String email;
    @Column(nullable = false)
    private String password;


}







