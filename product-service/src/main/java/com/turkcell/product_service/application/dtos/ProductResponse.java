package com.turkcell.product_service.application.dtos;

/**
 * Product Response DTO
 */
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private PriceDto price;
    private StockDto stock;

    public ProductResponse() {
    }

    public ProductResponse(String id, String name, String description, PriceDto price, StockDto stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriceDto getPrice() {
        return price;
    }

    public void setPrice(PriceDto price) {
        this.price = price;
    }

    public StockDto getStock() {
        return stock;
    }

    public void setStock(StockDto stock) {
        this.stock = stock;
    }
}