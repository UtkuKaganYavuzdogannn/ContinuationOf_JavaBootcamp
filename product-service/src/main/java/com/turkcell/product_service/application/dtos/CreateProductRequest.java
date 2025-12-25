package com.turkcell.product_service.application.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Create Product Request DTO
 */
public class CreateProductRequest {
    @NotBlank(message = "Ürün adı boş olamaz")
    @Size(min = 2, message = "Ürün adı minimum 2 karakter olmalıdır")
    private String name;

    @NotBlank(message = "Ürün açıklaması boş olamaz")
    @Size(min = 10, message = "Ürün açıklaması minimum 10 karakter olmalıdır")
    private String description;

    @NotNull(message = "Ürün fiyatı boş olamaz")
    @Valid
    private PriceDto price;

    @NotNull(message = "Ürün stok bilgisi boş olamaz")
    @Valid
    private StockDto stock;

    public CreateProductRequest() {
    }

    public CreateProductRequest(String name, String description, PriceDto price, StockDto stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
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