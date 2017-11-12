package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.OrderDao;
import com.mshulga.example.dao.jpa.OrderItemDao;
import com.mshulga.example.model.Order;
import com.mshulga.example.model.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    private static final BigDecimal ZERO_NUMBER = new BigDecimal(0);

    public Order create(Order order) {
        order.setTotalBill(ZERO_NUMBER);
        if (null == order.getOrderDate()) {
            order.setOrderDate(new Date());
        }
        enrichOrderData(order);

        Set<OrderItem> inputOrderItems = new HashSet<>();
        if (null != order.getOrderItems()) {
            inputOrderItems.addAll(order.getOrderItems());
            order.getOrderItems().clear();
        }
        Order savedOrder = orderDao.save(order);

        savedOrder.setOrderItems(inputOrderItems);
        inputOrderItems.stream().forEach(orderItem -> {
                    orderItem.setOrder(savedOrder);
                    orderItemDao.save(orderItem);
                }
        );

        return savedOrder;
    }

    public void update(Order order) {
        order.setTotalBill(ZERO_NUMBER);
        if (null == order.getOrderDate()) {
            order.setOrderDate(new Date());
        }

        Order oldOrder = orderDao.findOne(order.getId());
        enrichOrderData(order);

        if (null != oldOrder.getOrderItems()) {
            Map<Long, OrderItem> oldOrderItems = oldOrder.getOrderItems().stream().collect(
                    Collectors.toMap(OrderItem::getId, Function.identity()));
            Map<Long, OrderItem> newOrderItems = (null != order.getOrderItems()) ?
                    order.getOrderItems().stream().collect(
                            Collectors.toMap(OrderItem::getId, Function.identity())) :
                    new HashMap<>();

            oldOrderItems.forEach((k, v) -> {
                OrderItem currItem = newOrderItems.get(k);
                if (null == currItem) {
                    v.setOrder(null);
                    orderItemDao.save(v);
                }
            });
        }

        order.getOrderItems().stream().forEach(orderItem -> {
                    orderItem.setOrder(order);
                    orderItemDao.save(orderItem);
                }
        );
        orderDao.save(order);
    }

    public void remove(Order order) {
        orderDao.delete(order);
    }

    public Order get(Long id) {
        return orderDao.findOne(id);
    }

    public Iterable<Order> getAll() {
        return orderDao.findAll();
    }

    private void enrichOrderData(Order order) {
        if (null != order.getOrderItems() && !order.getOrderItems().isEmpty()) {
            enrichOrderItems(order);
            calculateBill(order);
        }
    }

    private void calculateBill(Order order) {
        order.getOrderItems().stream().forEach(orderItem -> {
            order.setTotalBill(order.getTotalBill().add(orderItem.getProduct().getPrice()
                    .multiply(new BigDecimal(orderItem.getQuantity()))));
        });
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
