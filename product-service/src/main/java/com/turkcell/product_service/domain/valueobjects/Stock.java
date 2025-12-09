package com.turkcell.product_service.domain.valueobjects;

import java.util.Objects;

/**
 * Stock Value Object - DDD'de değer nesnesi
 * Stok miktarını temsil eder ve değişmez (immutable) bir nesnedir
 */
public final class Stock {
    private final int quantity;

    public Stock(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stok miktarı negatif olamaz");
        }
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    /**
     * Stokun tükenip tükenmediğini kontrol eder
     */
    public boolean isOutOfStock() {
        return quantity == 0;
    }

    /**
     * Stokun yeterli olup olmadığını kontrol eder
     */
    public boolean hasEnoughStock(int requestedQuantity) {
        if (requestedQuantity < 0) {
            throw new IllegalArgumentException("İstenen miktar negatif olamaz");
        }
        return quantity >= requestedQuantity;
    }

    /**
     * Stoktan belirli miktar çıkarır
     */
    public Stock reduce(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Çıkarılacak miktar negatif olamaz");
        }
        if (amount > quantity) {
            throw new IllegalArgumentException("Yetersiz stok. Mevcut: " + quantity + ", İstenen: " + amount);
        }
        return new Stock(quantity - amount);
    }

    /**
     * Stoka belirli miktar ekler
     */
    public Stock add(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Eklenen miktar negatif olamaz");
        }
        return new Stock(quantity + amount);
    }

    /**
     * Stok miktarını günceller
     */
    public Stock update(int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Stok miktarı negatif olamaz");
        }
        return new Stock(newQuantity);
    }

    /**
     * İki stok miktarını karşılaştırır
     */
    public boolean isGreaterThan(Stock other) {
        return this.quantity > other.quantity;
    }

    /**
     * İki stok miktarını karşılaştırır
     */
    public boolean isLessThan(Stock other) {
        return this.quantity < other.quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Stock stock = (Stock) o;
        return quantity == stock.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }

    @Override
    public String toString() {
        return String.valueOf(quantity);
    }
}