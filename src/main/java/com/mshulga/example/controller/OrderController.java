package com.mshulga.example.controller;

import com.mshulga.example.model.Order;
import com.mshulga.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        Order order = service.get(id);
        if (null == order) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = service.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number of records found", String.valueOf(orders.size()));
        return new ResponseEntity<>(orders, headers, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Order addedOrder = service.create(order);
        return new ResponseEntity<>(addedOrder, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id, @RequestBody Order order) {
        service.update(order);
        HttpHeaders headers = new HttpHeaders();
        Order searchedOrder = service.get(id);
        if (null == order) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (null == searchedOrder) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        headers.add("Order was updated  - ", String.valueOf(id));
        return new ResponseEntity<>(order, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable("id") Long id) {
        boolean isRemoved = service.remove(id);
        if (isRemoved) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Order was removed - ", String.valueOf(id));
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
