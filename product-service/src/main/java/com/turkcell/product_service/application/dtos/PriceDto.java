package com.turkcell.product_service.application.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Price Data Transfer Object
 */
public class PriceDto {
    @NotNull(message = "Fiyat miktarı boş olamaz")
    @Positive(message = "Fiyat pozitif olmalıdır")
    private BigDecimal amount;

    @NotNull(message = "Para birimi boş olamaz")
    private String currency;

    public PriceDto() {
    }

    public PriceDto(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}