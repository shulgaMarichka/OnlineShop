package com.mshulga.example.dao.jpa;

import com.mshulga.example.model.Category;
import com.mshulga.example.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDao extends CrudRepository<Product, Long> {

    List<Product> findByCategoryIn(Category category);

}
