package com.mshulga.example.controller;

import com.mshulga.example.model.Order;
import com.mshulga.example.service.OrderService;
import com.mshulga.example.service.es.EsOrdersService;
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
@RequestMapping("/orders")
public class OrderController {

    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService service;

    @Autowired
    private EsOrdersService esOrdersService;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable("id") Long id) {
        Order order = service.get(id);
        if (null == order) {
            LOG.info("Order wasn't found by id:{}.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        Iterable<Order> orders = service.getAll();
        LOG.info("Search result contains {} orders.", ((Collection<?>) orders).size());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order addedOrder = service.create(order);
        esOrdersService.save(service.get(addedOrder.getId()));
        LOG.info("Order was saved into ElasticSearch.");
        return new ResponseEntity<>(addedOrder, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable("id") Long id,
                                             @Valid @RequestBody Order order) {
        Order searchedOrder = service.get(id);
        if (null == searchedOrder) {
            LOG.info("Order with id:{} wasn't updated, because it doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        order.setId(id);
        service.update(order);
        esOrdersService.save(service.get(searchedOrder.getId()));
        LOG.info("Order was saved in ElasticSearch.");
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrder(@PathVariable("id") Long id) {
        Order order = service.get(id);
        if (null == order) {
            LOG.info("Order with id:{} wasn't deleted, because it doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.remove(order);
        esOrdersService.delete(order);
        LOG.info("Order was removed from ElasticSearch.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Iterable<Order>> getOrdersByProduct(
            @RequestParam(value = "productName") String productName) {
        Iterable orders = esOrdersService.findByProductName(productName);
        LOG.info("Search result by query:'{}' contains {} orders.", productName,
                ((Collection<?>) orders).size());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}