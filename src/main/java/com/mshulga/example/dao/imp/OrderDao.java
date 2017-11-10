package com.mshulga.example.dao.imp;

import com.mshulga.example.dao.GenericDao;
import com.mshulga.example.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderDao extends GenericDao<Order> {

    @Override
    public Class<Order> getPersistentClass() {
        return Order.class;
    }

}
