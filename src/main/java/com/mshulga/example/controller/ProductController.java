package com.mshulga.example.controller;

import com.mshulga.example.model.Category;
import com.mshulga.example.model.Product;
import com.mshulga.example.service.CategoryService;
import com.mshulga.example.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/categories")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{idCategory}/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("idCategory") Long idCategory,
                                              @PathVariable("id") Long id) {
        Category category = categoryService.get(idCategory);
        Product product = productService.get(id);
        if (null == category || null == product) {
            LOG.info("Product wasn't found by id:{} in category {}.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/{idCategory}/products")
    public ResponseEntity<Iterable<Product>> getAllProductsByCategory(@PathVariable("idCategory")
                                                                              Long idCategory) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            LOG.info("Products wasn't found by category id:{}.", idCategory);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Iterable<Product> products = productService.getAllByCategory(category);
        LOG.info("Search result contains {} products by category id:{}.",
                ((Collection<?>) products).size(), idCategory);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        Iterable<Product> products = productService.getAll();
        LOG.info("Search result contains {} products.", ((Collection<?>) products).size());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/{idCategory}/products")
    public ResponseEntity<Product> createProduct(@PathVariable("idCategory") Long idCategory,
                                                 @Valid @RequestBody Product product) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            LOG.info("Product wasn't created, because  category id:{} doesn't exist.", idCategory);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setCategory(category);
        Product addedProduct = productService.create(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }

    @PutMapping("/{idCategory}/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("idCategory") Long idCategory,
                                                 @Valid @PathVariable("id") Long id,
                                                 @RequestBody Product product) {
        Category category = categoryService.get(idCategory);
        if (null == category) {
            LOG.info("Product wasn't updated, because  category id:{} doesn't exist.", idCategory);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        product.setCategory(category);
        product.setId(id);
        Product searchedProduct = productService.get(id);
        if (null == searchedProduct) {
            LOG.info("Product id:{} wasn't updated, it doesn't exist.", id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.update(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{idCategory}/products/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("idCategory") Long idCategory,
                                              @PathVariable("id") Long id) {
        Category category = categoryService.get(idCategory);
        Product product = productService.get(id);
        if (null == category || null == product) {
            LOG.info("Product id:{} wasn't deleted by category id:{}, it doesn't exist.", id, idCategory);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.remove(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
