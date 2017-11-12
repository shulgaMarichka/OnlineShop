package com.mshulga.example.dao.impl;

import com.mshulga.example.dao.GenericDao;
import com.mshulga.example.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDao extends GenericDao<Product> {

    @Override
    public Class<Product> getPersistentClass() {
        return Product.class;
    }

}
