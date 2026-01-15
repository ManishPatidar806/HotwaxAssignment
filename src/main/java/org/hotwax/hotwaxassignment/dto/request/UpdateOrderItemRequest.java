package org.hotwax.hotwaxassignment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderItemRequest {
    private Integer quantity;
    private String status;
}

