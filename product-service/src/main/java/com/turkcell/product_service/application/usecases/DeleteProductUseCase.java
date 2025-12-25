package com.turkcell.product_service.application.usecases;

import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.web.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Delete Product Use Case
 */
@Service
public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void execute(String id) {
        Product.ProductId productId = Product.ProductId.fromString(id);

        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Ürün bulunamadı: " + id);
        }

        productRepository.deleteById(productId);
    }
}