package com.mshulga.example.service;

import com.mshulga.example.dao.impl.OrderItemDao;
import com.mshulga.example.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    public OrderItem create(OrderItem orderItem) {
        return orderItemDao.create(orderItem);
    }

    public void update(OrderItem orderItem) {
        orderItemDao.update(orderItem);
    }

    public boolean remove(Long id) {
        return orderItemDao.remove(orderItemDao.getObjectById(id));
    }

    public OrderItem get(Long id) {
        return orderItemDao.getObjectById(id);
    }

    public List<OrderItem> getAll() {
        return orderItemDao.getAll();
    }


}
