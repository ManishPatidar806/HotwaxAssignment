package org.hotwax.hotwaxassignment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderRequest {
    private Integer shippingContactMechId;
    private Integer billingContactMechId;
}
