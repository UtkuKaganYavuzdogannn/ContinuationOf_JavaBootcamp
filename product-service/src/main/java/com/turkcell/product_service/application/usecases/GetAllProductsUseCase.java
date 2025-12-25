package com.turkcell.product_service.application.usecases;

import com.turkcell.product_service.application.dtos.PriceDto;
import com.turkcell.product_service.application.dtos.ProductListResponse;
import com.turkcell.product_service.application.dtos.ProductResponse;
import com.turkcell.product_service.application.dtos.StockDto;
import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Get All Products Use Case
 */
@Service
public class GetAllProductsUseCase {
    private final ProductRepository productRepository;

    public GetAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductListResponse execute() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productResponses = products.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());

        return new ProductListResponse(productResponses, productResponses.size());
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