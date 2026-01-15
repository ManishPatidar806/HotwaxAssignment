package org.hotwax.hotwaxassignment.service;

import org.hotwax.hotwaxassignment.dto.request.OrderItemRequest;
import org.hotwax.hotwaxassignment.dto.request.OrderRequest;
import org.hotwax.hotwaxassignment.dto.request.UpdateOrderItemRequest;
import org.hotwax.hotwaxassignment.dto.request.UpdateOrderRequest;
import org.hotwax.hotwaxassignment.dto.response.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(int orderId);
    OrderResponse updateOrder(int orderId, UpdateOrderRequest updateOrderRequest);
    void deleteOrder(int orderId);
    OrderResponse addOrderItem(int orderId, OrderItemRequest orderItemRequest);
    OrderResponse updateOrderItem(int orderId, int orderItemSeqId, UpdateOrderItemRequest updateOrderItemRequest);
    void deleteOrderItem(int orderId, int orderItemSeqId);
}

