package com.turkcell.product_service.application.dtos;

import java.util.List;

/**
 * Product List Response DTO
 */
public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalCount;

    public ProductListResponse() {
    }

    public ProductListResponse(List<ProductResponse> products, int totalCount) {
        this.products = products;
        this.totalCount = totalCount;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}