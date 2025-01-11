package com.example.advancedassignment4.controller;

import com.example.advancedassignment4.model.Product;
import com.example.advancedassignment4.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Use this so all the mappings start with "/api/v1/product"
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Use the repository to get all products, then return as JSON along with an OK response
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Use the repository to find the product specified in the "/api/v1/product/{id}" path. If the product exists, get it as JSON with OK status, if not, throw an error with a message and NOT_FOUND status
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "That product does not exist."));
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Take a product that is posted. The error handling for this is handled within the Product entity
    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    // Use the repository to find the product specified in the "/api/v1/product/{id}" path. If the product exists and is valid (see Product class), replace the information of the old product with the new one and return said product + OK status. If not, either throw because the product does not exist, or throw because it's not valid
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(updatedProduct.getName());
                    existingProduct.setPrice(updatedProduct.getPrice());
                    existingProduct.setQuantity(updatedProduct.getQuantity());
                    Product savedProduct = productRepository.save(existingProduct);
                    return new ResponseEntity<>(savedProduct, HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "That product does not exist."));
    }

    // Use the repository to find the product specified in the "/api/v1/product/{id}" path. If it exists, delete it and return a conformation message + OK status. If not, throw because it does not exist along with NOT_FOUND status
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id).
                map(product -> {
                    productRepository.delete(product);
                    Map<String, String> response = new HashMap<>();
                    response.put("message", "Product has been deleted");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }





}
