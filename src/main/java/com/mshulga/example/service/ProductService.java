package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.ProductDao;
import com.mshulga.example.model.Category;
import com.mshulga.example.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductDao productDao;

    public Product create(Product product) {
        Product savedProduct = productDao.save(product);
        LOG.info("Product was saved into data base.");
        return savedProduct;
    }

    public void update(Product product) {
        productDao.save(product);
        LOG.info("Product was updated in data base.");
    }

    public void remove(Product product) {
        productDao.delete(product);
        LOG.info("Product was removed from data base.");
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
