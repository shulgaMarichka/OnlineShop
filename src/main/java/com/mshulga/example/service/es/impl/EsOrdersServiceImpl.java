package com.mshulga.example.service.es.impl;

import com.mshulga.example.dao.es.EsOrdersRepository;
import com.mshulga.example.model.Order;
import com.mshulga.example.service.es.EsOrdersService;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EsOrdersServiceImpl implements EsOrdersService {

    private static final String SEARCH_PATH = "orderItems.product.name:";

    @Autowired
    private EsOrdersRepository esRepository;

    @Override
    public Order save(Order order) {
        return esRepository.save(order);
    }

    @Override
    public void delete(Order order) {
        esRepository.delete(order);
    }

    @Override
    public Order findOne(Long id) {
        return esRepository.findOne(id);
    }

    @Override
    public Iterable<Order> findAll() {
        return esRepository.findAll();
    }


    public Iterable<Order> findByProductName(String productName) {
        Page<Order> result = ((Page<Order>) esRepository.search(QueryBuilders.queryStringQuery(SEARCH_PATH +
                productName)));
        return result.getContent();
    }
}
