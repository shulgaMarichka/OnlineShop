package com.mshulga.example.controller;

import com.mshulga.example.model.OrderItem;
import com.mshulga.example.service.OrderItemService;
import com.mshulga.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable("id") Long id) {
        OrderItem orderItem = service.get(id);
        if (null == orderItem) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = service.getAll();
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItem orderItem) {
        OrderItem addedOrderItem = service.create(orderItem);
        return new ResponseEntity<>(addedOrderItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable("id") Long id, @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        service.update(orderItem);
        OrderItem searchedOrderItem = service.get(id);
        if (null == orderItem) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (null == searchedOrderItem) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrderItem(@PathVariable("id") Long id) {
        boolean isRemoved = service.remove(id);
        if (isRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
