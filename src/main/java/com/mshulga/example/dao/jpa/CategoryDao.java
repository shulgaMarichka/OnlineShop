package com.mshulga.example.dao.jpa;

import com.mshulga.example.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryDao extends CrudRepository<Category, Long> {

}
