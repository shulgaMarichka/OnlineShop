package com.mshulga.example.controller;

import com.mshulga.example.model.Product;
import com.mshulga.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product product = service.get(id);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProduct() {
        List<Product> products = service.getAll();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Number of records found", String.valueOf(products.size()));
        return new ResponseEntity<>(products, headers, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product addedProduct = service.create(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
        service.update(product);
        HttpHeaders headers = new HttpHeaders();
        Product searchedProduct = service.get(id);
        if (null == product) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (null == searchedProduct) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        headers.add("Product was updated  - ", String.valueOf(id));
        return new ResponseEntity<>(product, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeProduct(@PathVariable("id") Long id) {
        boolean isRemoved = service.remove(id);
        if (isRemoved) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Product was removed - ", String.valueOf(id));
            return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
