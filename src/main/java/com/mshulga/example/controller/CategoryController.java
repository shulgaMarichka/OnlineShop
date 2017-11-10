package com.mshulga.example.controller;

import com.mshulga.example.model.Category;
import com.mshulga.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = service.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number of records found", String.valueOf(categories.size()));
        return new ResponseEntity<>(categories, headers, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category addedCategory = service.create(category);
        return new ResponseEntity<>(addedCategory, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category category) {
        service.update(category);
        HttpHeaders headers = new HttpHeaders();
        Category searchedCategory = service.get(id);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (null == searchedCategory) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        headers.add("Category was updated  - ", String.valueOf(id));
        return new ResponseEntity<>(category, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCategory(@PathVariable("id") Long id) {
        boolean isRemoved = service.remove(id);
        if (isRemoved) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Category was removed - ", String.valueOf(id));
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
