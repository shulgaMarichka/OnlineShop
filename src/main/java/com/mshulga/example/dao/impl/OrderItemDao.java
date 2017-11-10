package com.mshulga.example.dao.impl;

import com.mshulga.example.dao.GenericDao;
import com.mshulga.example.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemDao extends GenericDao<OrderItem> {

    @Override
    public Class<OrderItem> getPersistentClass() {
        return OrderItem.class;
    }

    @Override
    public OrderItem create(OrderItem orderItem) {
        setReverseProductRel(orderItem);
        super.create(orderItem);
        return orderItem;
    }

    @Override
    public OrderItem update(OrderItem orderItem) {
        setReverseProductRel(orderItem);
        super.update(orderItem);
        return orderItem;
    }

    private void setReverseProductRel(OrderItem orderItem) {
        if (null != orderItem.getProduct()) {
            orderItem.getProduct().setItem(orderItem);
        }
    }


}