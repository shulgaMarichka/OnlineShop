package com.mshulga.example.service.es;

import com.mshulga.example.model.Order;

public interface EsOrdersService {

    Order save(Order order);

    void delete(Order order);

    Order findOne(Long id);

    Iterable<Order> findAll();

    Iterable<Order> findByProductName(String productName);

}
