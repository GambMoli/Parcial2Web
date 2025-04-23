package com.example.parcial2web.services;

import com.example.parcial2web.dtos.product.CreateProductDTO;
import com.example.parcial2web.dtos.product.ResponseProductDTO;
import com.example.parcial2web.exceptions.CustomException;
import com.example.parcial2web.mappers.ProductMapper;
import com.example.parcial2web.models.Product;
import com.example.parcial2web.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductRepository productRepository;

    public ResponseProductDTO createProduct(CreateProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public List<ResponseProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new CustomException("No existe un producto con ese id", 404)
        );
        return productMapper.toDto(product);
    }

    public ResponseProductDTO updateProduct(Long id, CreateProductDTO dto) {
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new CustomException("No existe un producto con ese id", 404)
        );

        Product updatedProductData = productMapper.toEntity(dto);
        updatedProductData.setId(existingProduct.getId());

        Product savedProduct = productRepository.save(updatedProductData);
        return productMapper.toDto(savedProduct);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException("No existe un producto con ese id", 404);
        }
        productRepository.deleteById(id);
    }


}