package com.mshulga.example.controller;

import com.mshulga.example.model.OrderItem;
import com.mshulga.example.service.OrderItemService;
import com.mshulga.example.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderItemController.class);

    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable("id") Long id) {
        OrderItem orderItem = service.get(id);
        if (null == orderItem) {
            LOG.info("Order item wasn't found by id:{}.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<OrderItem>> getAllOrderItems() {
        Iterable<OrderItem> orderItems = service.getAll();
        LOG.info("Search result contains {} order items.",
                ((Collection<?>) orderItems).size());
        return new ResponseEntity<>(orderItems, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderItem> createOrderItem(@Valid @RequestBody OrderItem orderItem) {
        OrderItem addedOrderItem = service.create(orderItem);
        return new ResponseEntity<>(addedOrderItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable("id") Long id,
                                                     @Valid @RequestBody OrderItem orderItem) {
        orderItem.setId(id);
        OrderItem searchedOrderItem = service.get(id);
        if (null == searchedOrderItem) {
            LOG.info("Order item with id:{} wasn't updated, because it doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.update(orderItem);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrderItem(@PathVariable("id") Long id) {
        OrderItem orderItem = service.get(id);
        if (null == orderItem) {
            LOG.info("Order item with id:{} wasn't deleted, because it doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.remove(orderItem);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
