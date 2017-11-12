package com.mshulga.example.controller;

import com.mshulga.example.model.Order;
import com.mshulga.example.service.OrderService;
import com.mshulga.example.service.es.EsOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private EsOrdersService esOrdersService;

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
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order addedOrder = service.create(order);
        esOrdersService.save(service.get(addedOrder.getId()));
        return new ResponseEntity<>(addedOrder, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id,
                                             @Valid @RequestBody Order order) {
        Order searchedOrder = service.get(id);
        if (null == searchedOrder) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order.setId(id);
        service.update(order);
        esOrdersService.save(service.get(searchedOrder.getId()));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable("id") Long id) {
        Order removedOrder = service.get(id);
        boolean isRemoved = service.remove(id);
        if (isRemoved) {
            esOrdersService.delete(removedOrder);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Order>> getOrdersByProduct(
            @RequestParam(value = "productName") String productName) {
        Iterable orders = esOrdersService.findByProductName(productName);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}