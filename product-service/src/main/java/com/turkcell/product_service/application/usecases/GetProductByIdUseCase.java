package com.turkcell.product_service.application.usecases;

import com.turkcell.product_service.application.dtos.PriceDto;
import com.turkcell.product_service.application.dtos.ProductResponse;
import com.turkcell.product_service.application.dtos.StockDto;
import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.web.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Get Product By ID Use Case
 */
@Service
public class GetProductByIdUseCase {
    private final ProductRepository productRepository;

    public GetProductByIdUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse execute(String id) {
        Product.ProductId productId = Product.ProductId.fromString(id);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));

        return toProductResponse(product);
    }

    private ProductResponse toProductResponse(Product product) {
        PriceDto priceDto = new PriceDto(
                product.getPrice().getAmount(),
                product.getPrice().getCurrency().getCode());

        StockDto stockDto = new StockDto(product.getStock().getQuantity());

        return new ProductResponse(
                product.getId().toString(),
                product.getName(),
                product.getDescription(),
                priceDto,
                stockDto);
    }
}