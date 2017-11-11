package com.mshulga.example.dao.impl;

import com.mshulga.example.dao.GenericDao;
import com.mshulga.example.model.Category;
import com.mshulga.example.model.OrderItem;
import com.mshulga.example.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ProductDao extends GenericDao<Product> {

    @Override
    public Class<Product> getPersistentClass() {
        return Product.class;
    }

    @Override
    public Product create(Product product) {
        super.create(product);
        return product;
    }

    @Override
    public Product update(Product product) {
        super.update(product);
        return product;
    }

}
