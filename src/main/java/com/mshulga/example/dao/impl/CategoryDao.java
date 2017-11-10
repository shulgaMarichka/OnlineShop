package com.mshulga.example.dao.impl;

import com.mshulga.example.dao.GenericDao;
import com.mshulga.example.model.Category;
import com.mshulga.example.model.Product;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CategoryDao extends GenericDao<Category> {

    @Override
    public Class<Category> getPersistentClass() {
        return Category.class;
    }

    @Override
    public Category create(Category category) {
        if (!category.getProducts().isEmpty()) {
            setReverseProductRel(category);
        }
        super.create(category);
        return category;
    }

    @Override
    public Category update(Category category) {
        if (!category.getProducts().isEmpty()) {
            setReverseProductRel(category);
        }
        super.update(category);
        return category;
    }

    private void setReverseProductRel(Category category) {
        category.getProducts().stream().forEach(product ->
            product.setCategory(category)
        );
    }
}