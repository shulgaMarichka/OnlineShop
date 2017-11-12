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
}
