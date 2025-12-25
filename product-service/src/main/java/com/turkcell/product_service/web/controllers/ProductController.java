package com.turkcell.product_service.web.controllers;

import com.turkcell.product_service.application.dtos.CreateProductRequest;
import com.turkcell.product_service.application.dtos.ProductListResponse;
import com.turkcell.product_service.application.dtos.ProductResponse;
import com.turkcell.product_service.application.dtos.UpdateProductRequest;
import com.turkcell.product_service.application.ports.ProductServicePort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Product REST Controller
 * RESTful API endpoint'lerini yönetir
 * Dependency Inversion Principle: Interface'e bağımlı (ProductServicePort)
 */
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductServicePort productService;

    public ProductController(ProductServicePort productService) {
        this.productService = productService;
    }

    /**
     * Tüm ürünleri listeler
     * GET /api/v1/products
     */
    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts() {
        ProductListResponse response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }

    /**
     * ID'ye göre ürün getirir
     * GET /api/v1/products/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse response = productService.getProductById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Yeni ürün oluşturur
     * POST /api/v1/products
     */
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Ürün günceller
     * PUT /api/v1/products/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Ürün siler
     * DELETE /api/v1/products/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
