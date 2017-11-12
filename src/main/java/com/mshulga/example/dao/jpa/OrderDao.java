package com.mshulga.example.dao.jpa;

import com.mshulga.example.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderDao extends CrudRepository<Order, Long> {

}
