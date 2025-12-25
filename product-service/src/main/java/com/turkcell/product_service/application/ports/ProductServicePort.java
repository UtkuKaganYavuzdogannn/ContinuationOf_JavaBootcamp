package com.turkcell.product_service.application.ports;

import com.turkcell.product_service.application.dtos.CreateProductRequest;
import com.turkcell.product_service.application.dtos.ProductListResponse;
import com.turkcell.product_service.application.dtos.ProductResponse;
import com.turkcell.product_service.application.dtos.UpdateProductRequest;

/**
 * Product Service Port Interface
 * Application layer'ın dış dünyaya açılan interface'i
 */
public interface ProductServicePort {
    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProductById(String id);

    ProductListResponse getAllProducts();

    ProductResponse updateProduct(String id, UpdateProductRequest request);

    void deleteProduct(String id);
}
