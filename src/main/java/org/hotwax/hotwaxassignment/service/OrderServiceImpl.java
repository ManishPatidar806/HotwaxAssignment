package org.hotwax.hotwaxassignment.service;

import org.hotwax.hotwaxassignment.dto.request.OrderItemRequest;
import org.hotwax.hotwaxassignment.dto.request.OrderRequest;
import org.hotwax.hotwaxassignment.dto.request.UpdateOrderItemRequest;
import org.hotwax.hotwaxassignment.dto.request.UpdateOrderRequest;
import org.hotwax.hotwaxassignment.dto.response.OrderResponse;
import org.hotwax.hotwaxassignment.entity.*;
import org.hotwax.hotwaxassignment.exception.ResourceNotFoundException;
import org.hotwax.hotwaxassignment.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final Order_HeaderRepository orderHeaderRepository;
    private final Order_ItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final Contact_MechRepository contactMechRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(Order_HeaderRepository orderHeaderRepository,
                            Order_ItemRepository orderItemRepository,
                            CustomerRepository customerRepository,
                            Contact_MechRepository contactMechRepository,
                            ProductRepository productRepository) {
        this.orderHeaderRepository = orderHeaderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
        this.contactMechRepository = contactMechRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + orderRequest.getCustomerId()));

        Contact_Mech shippingAddress = contactMechRepository.findById(orderRequest.getShippingContactMechId())
                .orElseThrow(() -> new ResourceNotFoundException("Shipping address not found with ID: " + orderRequest.getShippingContactMechId()));

        Contact_Mech billingAddress = contactMechRepository.findById(orderRequest.getBillingContactMechId())
                .orElseThrow(() -> new ResourceNotFoundException("Billing address not found with ID: " + orderRequest.getBillingContactMechId()));

        Order_Header orderHeader = new Order_Header();
        orderHeader.setOrder_date(orderRequest.getOrderDate());
        orderHeader.setCustomer(customer);
        orderHeader.setShippingContactMech(shippingAddress);
        orderHeader.setBillingContactMech(billingAddress);

        List<Order_Item> orderItems = new ArrayList<>();
        for (OrderRequest.OrderItems itemRequest : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + itemRequest.getProductId()));

            Order_Item orderItem = new Order_Item();
            orderItem.setOrderHeader(orderHeader);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setStatus(itemRequest.getStatus());
            orderItems.add(orderItem);
        }

        orderHeader.setOrderItems(orderItems);
        Order_Header savedOrder = orderHeaderRepository.save(orderHeader);

        return convertToOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(int orderId) {
        Order_Header orderHeader = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        return convertToOrderResponse(orderHeader);
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(int orderId, UpdateOrderRequest updateOrderRequest) {
        Order_Header orderHeader = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (updateOrderRequest.getShippingContactMechId() != null) {
            Contact_Mech shippingAddress = contactMechRepository.findById(updateOrderRequest.getShippingContactMechId())
                    .orElseThrow(() -> new ResourceNotFoundException("Shipping address not found with ID: " + updateOrderRequest.getShippingContactMechId()));
            orderHeader.setShippingContactMech(shippingAddress);
        }

        if (updateOrderRequest.getBillingContactMechId() != null) {
            Contact_Mech billingAddress = contactMechRepository.findById(updateOrderRequest.getBillingContactMechId())
                    .orElseThrow(() -> new ResourceNotFoundException("Billing address not found with ID: " + updateOrderRequest.getBillingContactMechId()));
            orderHeader.setBillingContactMech(billingAddress);
        }

        Order_Header updatedOrder = orderHeaderRepository.save(orderHeader);
        return convertToOrderResponse(updatedOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(int orderId) {
        Order_Header orderHeader = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        orderHeaderRepository.delete(orderHeader);
    }

    @Override
    @Transactional
    public OrderResponse addOrderItem(int orderId, OrderItemRequest orderItemRequest) {
        Order_Header orderHeader = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        Product product = productRepository.findById(orderItemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + orderItemRequest.getProductId()));

        Order_Item orderItem = new Order_Item();
        orderItem.setOrderHeader(orderHeader);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemRequest.getQuantity());
        orderItem.setStatus(orderItemRequest.getStatus());

        orderHeader.getOrderItems().add(orderItem);
        Order_Header updatedOrder = orderHeaderRepository.save(orderHeader);

        return convertToOrderResponse(updatedOrder);
    }

    @Override
    @Transactional
    public OrderResponse updateOrderItem(int orderId, int orderItemSeqId, UpdateOrderItemRequest updateOrderItemRequest) {
        Order_Header orderHeader;

        Order_Item orderItem = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with ID: " + orderItemSeqId));


        if (orderItem.getOrderHeader().getOrder_id() != orderId) {
            throw new ResourceNotFoundException("Order item " + orderItemSeqId + " does not belong to order " + orderId);
        }

        if (updateOrderItemRequest.getQuantity() != null) {
            orderItem.setQuantity(updateOrderItemRequest.getQuantity());
        }

        if (updateOrderItemRequest.getStatus() != null) {
            orderItem.setStatus(updateOrderItemRequest.getStatus());
        }

        orderItemRepository.save(orderItem);


        orderHeader = orderHeaderRepository.findById(orderId).orElseThrow();
        return convertToOrderResponse(orderHeader);
    }

    @Override
    @Transactional
    public void deleteOrderItem(int orderId, int orderItemSeqId) {
        Order_Header orderHeader = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        Order_Item orderItem = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with ID: " + orderItemSeqId));


        if (orderItem.getOrderHeader().getOrder_id() != orderId) {
            throw new ResourceNotFoundException("Order item " + orderItemSeqId + " does not belong to order " + orderId);
        }

        orderItemRepository.delete(orderItem);
    }

    private OrderResponse convertToOrderResponse(Order_Header orderHeader) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(orderHeader.getOrder_id());
        response.setOrderDate(orderHeader.getOrder_date());


        Customer customer = orderHeader.getCustomer();
        OrderResponse.CustomerInfo customerInfo = new OrderResponse.CustomerInfo(
                customer.getCustomer_id(),
                customer.getFirst_name(),
                customer.getLast_name(),
                customer.getEmail()
        );
        response.setCustomer(customerInfo);


        Contact_Mech shippingAddress = orderHeader.getShippingContactMech();
        OrderResponse.ContactMechInfo shippingInfo = new OrderResponse.ContactMechInfo(
                shippingAddress.getContact_mech_id(),
                shippingAddress.getStreet_address(),
                shippingAddress.getCity(),
                shippingAddress.getState(),
                shippingAddress.getPostal_code(),
                shippingAddress.getPhone_number(),
                shippingAddress.getEmail()
        );
        response.setShippingAddress(shippingInfo);


        Contact_Mech billingAddress = orderHeader.getBillingContactMech();
        OrderResponse.ContactMechInfo billingInfo = new OrderResponse.ContactMechInfo(
                billingAddress.getContact_mech_id(),
                billingAddress.getStreet_address(),
                billingAddress.getCity(),
                billingAddress.getState(),
                billingAddress.getPostal_code(),
                billingAddress.getPhone_number(),
                billingAddress.getEmail()
        );
        response.setBillingAddress(billingInfo);


        List<OrderResponse.OrderItemInfo> orderItemInfos = orderHeader.getOrderItems().stream()
                .map(orderItem -> {
                    Product product = orderItem.getProduct();
                    OrderResponse.ProductInfo productInfo = new OrderResponse.ProductInfo(
                            product.getProduct_id(),
                            product.getProduct_name(),
                            product.getColor(),
                            product.getSize()
                    );

                    return new OrderResponse.OrderItemInfo(
                            orderItem.getOrder_item_seq_id(),
                            productInfo,
                            orderItem.getQuantity(),
                            orderItem.getStatus()
                    );
                })
                .collect(Collectors.toList());

        response.setOrderItems(orderItemInfos);

        return response;
    }
}

