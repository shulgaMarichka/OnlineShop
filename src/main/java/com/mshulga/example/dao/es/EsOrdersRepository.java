package com.mshulga.example.dao.es;

import com.mshulga.example.model.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsOrdersRepository extends ElasticsearchRepository<Order, Long> {

}
