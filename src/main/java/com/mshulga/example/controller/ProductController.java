package com.mshulga.example.controller;

import com.mshulga.example.model.Category;
import com.mshulga.example.model.Product;
import com.mshulga.example.service.CategoryService;
import com.mshulga.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories/{idCategory}/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("idCategory") Long idCategory, @PathVariable("id") Long id) {
        Category category = categoryService.get(idCategory);
        Product product = productService.get(id);
        if (null == category || null == product) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct(@PathVariable("idCategory") Long idCategory) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Product> products = productService.getAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@PathVariable("idCategory") Long idCategory, @RequestBody Product product) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setCategory(category);
        Product addedProduct = productService.create(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("idCategory") Long idCategory, @PathVariable("id") Long id,
                    @RequestBody Product product) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setCategory(category);
        productService.update(product);
        Product searchedProduct = productService.get(id);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (null == searchedProduct) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("idCategory") Long idCategory, @PathVariable("id") Long id) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean isRemoved = productService.remove(id);
        if (isRemoved) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
