package com.mshulga.example.service;

import com.mshulga.example.dao.jpa.CategoryDao;
import com.mshulga.example.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public Category create(Category category) {
        setReverseProductRel(category);
        return categoryDao.save(category);
    }

    public void update(Category category) {
        setReverseProductRel(category);
        categoryDao.save(category);
    }

    public void remove(Category category) {
        categoryDao.delete(category);
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
