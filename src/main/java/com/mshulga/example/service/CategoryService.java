package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.CategoryDao;
import com.mshulga.example.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryDao categoryDao;

    public Category create(Category category) {
        setReverseProductRel(category);
        Category savedCategory = categoryDao.save(category);
        LOG.info("Category was saved into data base.");
        return savedCategory;
    }

    public void update(Category category) {
        setReverseProductRel(category);
        categoryDao.save(category);
        LOG.info("Category was updated in data base.");
    }

    public void remove(Category category) {
        categoryDao.delete(category);
        LOG.info("Category was removed from data base.");
    }

    public Category get(Long id) {
        return categoryDao.findOne(id);
    }

    public Iterable<Category> getAll() {
        return categoryDao.findAll();
    }

    private void setReverseProductRel(Category category) {
        if (null != category.getProducts() && !category.getProducts().isEmpty()) {
            category.getProducts().stream().forEach(product ->
                    product.setCategory(category)
            );
        }
    }

}
