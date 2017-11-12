package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.OrderItemDao;
import com.mshulga.example.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public OrderItem create(OrderItem orderItem) {
        return orderItemDao.save(orderItem);
    }

    public void update(OrderItem orderItem) {
        orderItemDao.save(orderItem);
    }

    public void remove(OrderItem orderItem) {
        orderItemDao.delete(orderItem);
    }

    public OrderItem get(Long id) {
        return orderItemDao.findOne(id);
    }

    public Iterable<OrderItem> getAll() {
        return orderItemDao.findAll();
    }


}
