package org.hotwax.hotwaxassignment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {
    private int productId;
    private int quantity;
    private String status;
}

