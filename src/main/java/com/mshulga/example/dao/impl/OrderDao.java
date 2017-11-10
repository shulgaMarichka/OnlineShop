package com.mshulga.example.dao.impl;

import com.mshulga.example.dao.GenericDao;
import com.mshulga.example.model.Order;
import com.mshulga.example.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderDao extends GenericDao<Order> {

    @Override
    public Class<Order> getPersistentClass() {
        return Order.class;
    }

    @Override
    public Order create(Order order) {
        setReverseOrderItemRel(order);
        super.create(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        setReverseOrderItemRel(order);
        super.update(order);
        return order;
    }

    private void setReverseOrderItemRel(Order order) {
        order.getOrderItems().stream().forEach(orderItem ->
                        orderItem.setOrder(order)
        );
    }

}
