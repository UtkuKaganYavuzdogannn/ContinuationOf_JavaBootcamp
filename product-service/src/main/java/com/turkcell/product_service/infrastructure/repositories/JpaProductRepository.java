package com.turkcell.product_service.infrastructure.repositories;

import com.turkcell.product_service.infrastructure.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * JPA Product Repository
 * Spring Data JPA interface'i
 */
@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, String> {
    List<ProductEntity> findByNameContaining(String name);

    List<ProductEntity> findByStockQuantityGreaterThan(Integer quantity);

    List<ProductEntity> findByStockQuantityEquals(Integer quantity);

    List<ProductEntity> findByPriceAmountBetween(BigDecimal minPrice, BigDecimal maxPrice);
}