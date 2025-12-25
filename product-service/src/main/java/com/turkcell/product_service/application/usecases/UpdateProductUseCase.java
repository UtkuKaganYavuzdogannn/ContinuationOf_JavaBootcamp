package com.turkcell.product_service.application.usecases;

import com.turkcell.product_service.application.dtos.PriceDto;
import com.turkcell.product_service.application.dtos.ProductResponse;
import com.turkcell.product_service.application.dtos.StockDto;
import com.turkcell.product_service.application.dtos.UpdateProductRequest;
import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.domain.valueobjects.Currency;
import com.turkcell.product_service.domain.valueobjects.Price;
import com.turkcell.product_service.domain.valueobjects.Stock;
import com.turkcell.product_service.web.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Update Product Use Case
 */
@Service
public class UpdateProductUseCase {
    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse execute(String id, UpdateProductRequest request) {
        Product.ProductId productId = Product.ProductId.fromString(id);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı: " + id));

        // Ürün bilgilerini güncelle
        product.updateProduct(request.getName(), request.getDescription());

        // Fiyat güncellemesi (eğer varsa)
        if (request.getPrice() != null) {
            Currency currency = Currency.fromCode(request.getPrice().getCurrency());
            Price newPrice = new Price(request.getPrice().getAmount(), currency);
            product.updatePrice(newPrice);
        }

        // Stok güncellemesi (eğer varsa)
        if (request.getStock() != null) {
            Stock newStock = new Stock(request.getStock().getQuantity());
            product.updateStock(newStock);
        }

        // Repository'ye kaydet
        Product updatedProduct = productRepository.save(product);

        return toProductResponse(updatedProduct);
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