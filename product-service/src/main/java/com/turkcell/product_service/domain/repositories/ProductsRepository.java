package com.turkcell.product_service.domain.repositories;

import com.turkcell.product_service.domain.entities.Product;
import com.turkcell.product_service.domain.entities.Product.ProductId;
import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    void save(Product product);

    Optional<Product> findById(ProductId id);

    List<Product> findAll();

    void update(Product product);

    void deleteById(ProductId id);

}
