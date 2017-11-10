package com.mshulga.example.dao.imp;

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
        Set<Product> products = new HashSet<>(category.getProducts());
        category.getProducts().clear();
        products.stream().forEach(product -> {
            product.setCategory(category);
        });
        category.setProducts(products);
    }
}
