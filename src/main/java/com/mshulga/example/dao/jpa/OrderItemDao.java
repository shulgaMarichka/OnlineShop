package com.mshulga.example.dao.jpa;

import com.mshulga.example.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

public interface OrderItemDao extends CrudRepository<OrderItem, Long> {

}
