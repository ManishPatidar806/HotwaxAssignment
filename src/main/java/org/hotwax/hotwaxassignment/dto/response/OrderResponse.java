package org.hotwax.hotwaxassignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private int orderId;
    private Date orderDate;
    private CustomerInfo customer;
    private ContactMechInfo shippingAddress;
    private ContactMechInfo billingAddress;
    private List<OrderItemInfo> orderItems;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerInfo {
        private int customerId;
        private String firstName;
        private String lastName;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContactMechInfo {
        private int contactMechId;
        private String streetAddress;
        private String city;
        private String state;
        private String postalCode;
        private String phoneNumber;
        private String email;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemInfo {
        private int orderItemSeqId;
        private ProductInfo product;
        private int quantity;
        private String status;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductInfo {
        private int productId;
        private String productName;
        private String color;
        private String size;
    }
}
