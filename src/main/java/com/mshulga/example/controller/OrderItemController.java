package com.mshulga.example.controller;

import com.mshulga.example.model.OrderItem;
import com.mshulga.example.model.Product;
import com.mshulga.example.service.OrderItemService;
import com.mshulga.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products/{idProduct}/order_items")
public class OrderItemController {

    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable("idProduct") Long idProduct, @PathVariable("id") Long id) {
        Product product = productService.get(idProduct);
        OrderItem orderItem = service.get(id);
        if (null == product || null == orderItem) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<OrderItem>> getAllOrderItems(@PathVariable("idProduct") Long idProduct) {
        Product product = productService.get(idProduct);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<OrderItem> orderItems = service.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number of records found", String.valueOf(orderItems.size()));
        return new ResponseEntity<>(orderItems, headers, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderItem> createOrderItem(@PathVariable("idProduct") Long idProduct,
                    @RequestBody OrderItem orderItem) {
        Product product = productService.get(idProduct);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderItem.setProduct(product);
        OrderItem addedOrderItem = service.create(orderItem);
        return new ResponseEntity<>(addedOrderItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable("idProduct") Long idProduct,
                    @PathVariable("id") Long id, @RequestBody OrderItem orderItem) {
        Product product = productService.get(idProduct);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderItem.setProduct(product);
        service.update(orderItem);
        HttpHeaders headers = new HttpHeaders();
        OrderItem searchedOrderItem = service.get(id);
        if (null == orderItem) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (null == searchedOrderItem) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        headers.add("Order item was updated  - ", String.valueOf(id));
        return new ResponseEntity<>(orderItem, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeOrderItem(@PathVariable("idProduct") Long idProduct,
                    @PathVariable("id") Long id) {
        Product product = productService.get(idProduct);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean isRemoved = service.remove(id);
        if (isRemoved) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Order item was removed - ", String.valueOf(id));
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
