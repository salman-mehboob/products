package com.theverygroup.products.controller;

import com.theverygroup.products.dto.Product;
import com.theverygroup.products.dto.Type;
import com.theverygroup.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    @PostMapping("/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody Product product) throws IOException {
        List<Product> productList = productRepository.findByProductId(product.getId());
        if(productList.isEmpty()){
                productRepository.save(product);
                return ResponseEntity.ok("New Product added successfully.");
        }else{return ResponseEntity.ok("Product with this ID already exist.");}
    }
    @GetMapping("/products/{kind}/{value}")  //kind(id=I or type=T) and value( value of id or type)
    public ResponseEntity<?> getProductByIdOrType(@PathVariable(value = "kind") String kind,
                                                  @PathVariable(value = "value") String value ) {
                List<Product> product =  productRepository.findByProductIdOrType(kind, value);
                if(product.isEmpty()){
                    return ResponseEntity.ok("No Product Found");
                }else {
                    return ResponseEntity.ok(product);
                }
    }
    @DeleteMapping("/products/{id}")
    public String deleteProduct(@PathVariable(value = "id") String id) {
        return productRepository.deleteByProductId(id);
    }

}

