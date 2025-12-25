package com.turkcell.product_service.application.usecases;

import com.turkcell.product_service.application.dtos.CreateProductRequest;
import com.turkcell.product_service.application.dtos.PriceDto;
import com.turkcell.product_service.application.dtos.ProductResponse;
import com.turkcell.product_service.application.dtos.StockDto;
import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.domain.valueobjects.Currency;
import com.turkcell.product_service.domain.valueobjects.Price;
import com.turkcell.product_service.domain.valueobjects.Stock;
import org.springframework.stereotype.Service;

/**
 * Create Product Use Case
 */
@Service
public class CreateProductUseCase {
    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse execute(CreateProductRequest request) {
        // DTO'dan domain objelerine dönüşüm
        Currency currency = Currency.fromCode(request.getPrice().getCurrency());
        Price price = new Price(request.getPrice().getAmount(), currency);
        Stock stock = new Stock(request.getStock().getQuantity());

        // Domain entity oluştur
        Product product = Product.create(
                request.getName(),
                request.getDescription(),
                price,
                stock);

        // Repository'ye kaydet
        Product savedProduct = productRepository.save(product);

        // Domain entity'den DTO'ya dönüşüm
        return toProductResponse(savedProduct);
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