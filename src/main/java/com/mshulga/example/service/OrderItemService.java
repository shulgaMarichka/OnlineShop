package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.OrderItemDao;
import com.mshulga.example.model.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderItemService.class);

    @Autowired
    private OrderItemDao orderItemDao;

    public OrderItem create(OrderItem orderItem) {
        OrderItem savedOrderItem = orderItemDao.save(orderItem);
        LOG.info("Order item was saved into data base.");
        return savedOrderItem;
    }

    public void update(OrderItem orderItem) {
        orderItemDao.save(orderItem);
        LOG.info("Order item was updated in data base.");
    }

    public void remove(OrderItem orderItem) {
        orderItemDao.delete(orderItem);
        LOG.info("Order item was removed from data base.");
    }

    public OrderItem get(Long id) {
        return orderItemDao.findOne(id);
    }

    public Iterable<OrderItem> getAll() {
        return orderItemDao.findAll();
    }


}
