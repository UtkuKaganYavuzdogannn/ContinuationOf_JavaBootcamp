package com.turkcell.product_service.infrastructure.repositories;

import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.repositories.ProductRepository;
import com.turkcell.product_service.infrastructure.entities.ProductEntity;
import com.turkcell.product_service.infrastructure.mappers.ProductMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Product Repository Implementation
 * Domain repository interface'inin infrastructure implementasyonu
 */
@Component
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;

    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Product save(Product product) {
        ProductEntity entity = ProductMapper.toEntity(product);
        ProductEntity savedEntity = jpaProductRepository.save(entity);
        return ProductMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Product.ProductId id) {
        return jpaProductRepository.findById(id.toString())
                .map(ProductMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll().stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByNameContaining(String name) {
        return jpaProductRepository.findByNameContaining(name).stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findInStockProducts() {
        return jpaProductRepository.findByStockQuantityGreaterThan(0).stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findOutOfStockProducts() {
        return jpaProductRepository.findByStockQuantityEquals(0).stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return jpaProductRepository
                .findByPriceAmountBetween(
                        java.math.BigDecimal.valueOf(minPrice),
                        java.math.BigDecimal.valueOf(maxPrice))
                .stream()
                .map(ProductMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Product.ProductId id) {
        jpaProductRepository.deleteById(id.toString());
    }

    @Override
    public boolean existsById(Product.ProductId id) {
        return jpaProductRepository.existsById(id.toString());
    }

    @Override
    public long count() {
        return jpaProductRepository.count();
    }

    @Override
    public long countInStockProducts() {
        return jpaProductRepository.findByStockQuantityGreaterThan(0).size();
    }
}