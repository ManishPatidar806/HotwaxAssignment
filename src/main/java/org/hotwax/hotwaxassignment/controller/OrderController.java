package org.hotwax.hotwaxassignment.controller;

import jakarta.validation.Valid;
import org.hotwax.hotwaxassignment.dto.request.OrderItemRequest;
import org.hotwax.hotwaxassignment.dto.request.OrderRequest;
import org.hotwax.hotwaxassignment.dto.request.UpdateOrderItemRequest;
import org.hotwax.hotwaxassignment.dto.request.UpdateOrderRequest;
import org.hotwax.hotwaxassignment.dto.response.OrderResponse;
import org.hotwax.hotwaxassignment.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        OrderResponse response = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable int orderId) {
        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable int orderId,
                                                      @Valid @RequestBody UpdateOrderRequest updateOrderRequest) {
        OrderResponse response = orderService.updateOrder(orderId, updateOrderRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderResponse> addOrderItem(@PathVariable int orderId,
                                                       @Valid @RequestBody OrderItemRequest orderItemRequest) {
        OrderResponse response = orderService.addOrderItem(orderId, orderItemRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<OrderResponse> updateOrderItem(@PathVariable int orderId,
                                                          @PathVariable int orderItemSeqId,
                                                          @Valid @RequestBody UpdateOrderItemRequest updateOrderItemRequest) {
        OrderResponse response = orderService.updateOrderItem(orderId, orderItemSeqId, updateOrderItemRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}/items/{orderItemSeqId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable int orderId,
                                                 @PathVariable int orderItemSeqId) {
        orderService.deleteOrderItem(orderId, orderItemSeqId);
        return ResponseEntity.noContent().build();
    }
}

