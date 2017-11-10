package com.mshulga.example.service;

import com.mshulga.example.dao.impl.OrderDao;
import com.mshulga.example.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public Order create(Order order) {
        return orderDao.create(order);
    }

    public void update(Order order) {
        orderDao.update(order);
    }

    public boolean remove(Long id) {
        return orderDao.remove(orderDao.getObjectById(id));
    }

    public Order get(Long id) {
        return orderDao.getObjectById(id);
    }

    public List<Order> getAll() {
        return orderDao.getAll();
    }


}
