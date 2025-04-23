package com.example.parcial2web.controllers;

import com.example.parcial2web.dtos.product.CreateProductDTO;
import com.example.parcial2web.dtos.product.ResponseProductDTO;
import com.example.parcial2web.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseProductDTO> createProduct(@RequestBody CreateProductDTO dto) {
        ResponseProductDTO createdProduct = productService.createProduct(dto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ResponseProductDTO>> getAllProducts() {
        List<ResponseProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> getProductById(@PathVariable Long id) {
        ResponseProductDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody CreateProductDTO dto) {
        ResponseProductDTO updatedProduct = productService.updateProduct(id, dto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}