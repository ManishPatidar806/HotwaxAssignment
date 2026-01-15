package org.hotwax.hotwaxassignment.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class OrderRequest {

    @Getter
    @Setter
    public static class OrderItems {
        @NotNull(message = "Product ID is required")
        @Positive(message = "Product ID must be positive")
        private int productId;

        @NotNull(message = "Quantity is required")
        @Positive(message = "Quantity must be positive")
        private int quantity;

        @NotNull(message = "Status is required")
        private String status;
    }

    @NotNull(message = "Customer ID is required")
    @Positive(message = "Customer ID must be positive")
    private int customerId;

    @NotNull(message = "Shipping address ID is required")
    @Positive(message = "Shipping address ID must be positive")
    private int shippingContactMechId;

    @NotNull(message = "Billing address ID is required")
    @Positive(message = "Billing address ID must be positive")
    private int billingContactMechId;

    @NotNull(message = "Order date is required")
    private Date orderDate;

    @NotEmpty(message = "Order must have at least one item")
    @Valid
    private List<OrderItems> orderItems;

}
