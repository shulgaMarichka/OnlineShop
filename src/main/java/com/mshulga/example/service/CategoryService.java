package com.mshulga.example.service;

import com.mshulga.example.dao.imp.CategoryDao;
import com.mshulga.example.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    public Category create(Category category) {
        return categoryDao.create(category);
    }

    public void update(Category category) {
        categoryDao.update(category);
    }

    public boolean remove(Long id) {
        return categoryDao.remove(categoryDao.getObjectById(id));
    }

    public Category get(Long id) {
        return categoryDao.getObjectById(id);
    }

    public List<Category> getAll() {
        return categoryDao.getAll();
    }


}
