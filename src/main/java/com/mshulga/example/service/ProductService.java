package com.mshulga.example.service;

import com.mshulga.example.dao.imp.ProductDao;
import com.mshulga.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product create(Product product) {
        return productDao.create(product);
    }

    public void update(Product product) {
        productDao.update(product);
    }

    public boolean remove(Long id) {
        return productDao.remove(productDao.getObjectById(id));
    }

    public Product get(Long id) {
        return productDao.getObjectById(id);
    }

    public List<Product> getAll() {
        return productDao.getAll();
    }

}
