package com.mshulga.example.controller;

import com.mshulga.example.model.Category;
import com.mshulga.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id) {
        Category category = service.get(id);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        Iterable<Category> categories = service.getAll();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category addedCategory = service.create(category);
        return new ResponseEntity<>(addedCategory, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
                                                   @Valid @RequestBody Category category) {
        category.setId(id);
        Category searchedCategory = service.get(id);
        if (null == searchedCategory) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.update(category);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable("id") Long id) {
        Category category = service.get(id);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.remove(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
