package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.OrderDao;
import com.mshulga.example.dao.jpa.OrderItemDao;
import com.mshulga.example.model.Order;
import com.mshulga.example.model.OrderItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    private static final BigDecimal ZERO_NUMBER = new BigDecimal(0);

    public Order create(Order order) {
        enrichOrderData(order);

        Order savedOrder = orderDao.save(order);

        savedOrder.getOrderItems().stream().forEach(orderItem -> {
            orderItem.setOrder(savedOrder);
            orderItemDao.save(orderItem);
        });
        LOG.info("Order was saved into data base.");
        return savedOrder;
    }

    public void update(Order order) {
        Order oldOrder = orderDao.findOne(order.getId());
        enrichOrderData(order);
        if (null != oldOrder.getOrderItems()) {
            Map<Long, OrderItem> oldOrderItems = mapOrderItems(oldOrder);
            Map<Long, OrderItem> newOrderItems = mapOrderItems(order);

            oldOrderItems.forEach((orderItemId, orderItem) -> {
                OrderItem currItem = newOrderItems.get(orderItemId);
                if (null == currItem) {
                    orderItem.setOrder(null);
                    orderItemDao.save(orderItem);
                }
            });
        }
        order.getOrderItems().stream().forEach(orderItem -> {
            orderItem.setOrder(order);
            orderItemDao.save(orderItem);
        });
        orderDao.save(order);
        LOG.info("Order was updated in data base.");
    }

    public void remove(Order order) {
        orderDao.delete(order);
        LOG.info("Order was removed from data base.");
    }

    public Order get(Long id) {
        return orderDao.findOne(id);
    }

    public Iterable<Order> getAll() {
        return orderDao.findAll();
    }

    private void enrichOrderData(Order order) {
        order.setTotalBill(ZERO_NUMBER);
        if (null == order.getOrderDate()) {
            order.setOrderDate(new Date());
        }
        if (null != order.getOrderItems()) {
            enrichOrderItems(order);
            calculateBill(order);
        } else {
            order.setOrderItems(new HashSet<>());
        }
    }

    private void calculateBill(Order order) {
        order.getOrderItems().stream().forEach(orderItem ->
                order.setTotalBill(order.getTotalBill().add(orderItem.getProduct().getPrice()
                        .multiply(new BigDecimal(orderItem.getQuantity()))))
        );
    }

    private Map<Long, OrderItem> mapOrderItems(Order order) {
        return order.getOrderItems().stream().collect(
                Collectors.toMap(OrderItem::getId, Function.identity()));
    }

    private void enrichOrderItems(Order order) {
        Set<OrderItem> inputOrderItems = new HashSet<>();
        order.getOrderItems().forEach(orderItem -> {
            if (null != orderItem.getId()) {
                OrderItem currItem = orderItemDao.findOne(orderItem.getId());
                if (null != currItem) {
                    inputOrderItems.add(currItem);
                }
            }
        });
        order.setOrderItems(inputOrderItems);
    }


}
