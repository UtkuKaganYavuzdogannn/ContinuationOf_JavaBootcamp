package com.turkcell.product_service.domain.valueobjects;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Price Value Object - DDD'de değer nesnesi
 * Fiyat bilgisini temsil eder ve değişmez (immutable) bir nesnedir
 */
public final class Price {
    private final BigDecimal amount;
    private final Currency currency;

    public Price(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new IllegalArgumentException("Fiyat miktarı null olamaz");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Para birimi null olamaz");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Fiyat negatif olamaz");
        }

        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * İki fiyatı karşılaştırır (aynı para biriminde olmalı)
     */
    public boolean isGreaterThan(Price other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Farklı para birimlerindeki fiyatlar karşılaştırılamaz");
        }
        return this.amount.compareTo(other.amount) > 0;
    }

    /**
     * İki fiyatı karşılaştırır (aynı para biriminde olmalı)
     */
    public boolean isLessThan(Price other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Farklı para birimlerindeki fiyatlar karşılaştırılamaz");
        }
        return this.amount.compareTo(other.amount) < 0;
    }

    /**
     * Fiyatı belirli bir yüzde ile artırır
     */
    public Price increaseByPercentage(BigDecimal percentage) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Yüzde pozitif olmalıdır");
        }

        BigDecimal multiplier = BigDecimal.ONE.add(percentage.divide(new BigDecimal("100")));
        BigDecimal newAmount = this.amount.multiply(multiplier);
        return new Price(newAmount, this.currency);
    }

    /**
     * Fiyatı belirli bir yüzde ile azaltır
     */
    public Price decreaseByPercentage(BigDecimal percentage) {
        if (percentage == null || percentage.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Yüzde pozitif olmalıdır");
        }

        BigDecimal multiplier = BigDecimal.ONE.subtract(percentage.divide(new BigDecimal("100")));
        BigDecimal newAmount = this.amount.multiply(multiplier);
        return new Price(newAmount, this.currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount) && Objects.equals(currency, price.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency.getCode();
    }
}