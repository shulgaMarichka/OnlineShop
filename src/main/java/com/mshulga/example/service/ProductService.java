package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.ProductDao;
import com.mshulga.example.model.Category;
import com.mshulga.example.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductDao productDao;

    public Product create(Product product) {
        return productDao.save(product);
    }

    public void update(Product product) {
        productDao.save(product);
    }

    public void remove(Product product) {
        productDao.delete(product);
    }

    public Product get(Long id) {
        return productDao.findOne(id);
    }

    public Iterable<Product> getAllByCategory(Category category) {
        return productDao.findByCategoryIn(category);
    }

    public Iterable<Product> getAll() {
        return productDao.findAll();
    }


}
