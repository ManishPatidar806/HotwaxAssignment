package org.hotwax.hotwaxassignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Order_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int order_item_seq_id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order_Header orderHeader;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 20)
    private String status;

}
