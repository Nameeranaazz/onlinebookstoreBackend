package com.onlineBookStore.controller;

import com.onlineBookStore.model.Order;
import com.onlineBookStore.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.findAll();
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order orderRequest) {
        Order order = new Order();
        order.setTotal(orderRequest.getTotal());
        order.setBookNames(orderRequest.getBookNames());

        Order savedOrder = orderService.saveOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

}

