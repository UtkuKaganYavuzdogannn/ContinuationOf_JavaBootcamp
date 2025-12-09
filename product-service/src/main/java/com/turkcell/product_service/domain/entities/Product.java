package com.turkcell.product_service.domain.entities;

import com.turkcell.product_service.domain.valueobjects.Price;
import com.turkcell.product_service.domain.valueobjects.Stock;
import java.util.Objects;
import java.util.UUID;

public class Product {
    private final ProductId id;

    private String name;
    private String description;
    private Price price;
    private Stock stock;

    // Private constructor - factory method kullanılacak
    private Product(ProductId id, String name, String description, Price price, Stock stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Factory method
    public static Product create(String name, String description, Price price, Stock stock) {

        validateProductData(name, description, price, stock);

        ProductId productId = ProductId.generate();

        return new Product(productId, name, description, price, stock);
    }

    // Mevcut ürünü yeniden oluşturur (repository'den yüklerken) rehydration da
    // diyebiliriz.

    public static Product reconstruct(ProductId id, String name, String description, Price price, Stock stock) {
        validateProductData(name, description, price, stock);

        return new Product(id, name, description, price, stock);
    }

    private static void validateProductData(String name, String description, Price price, Stock stock) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ürün adı null veya boş olamaz");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Ürün açıklaması null veya boş olamaz");
        }
        if (price == null) {
            throw new IllegalArgumentException("Ürün fiyatı null olamaz");
        }
        if (stock == null) {
            throw new IllegalArgumentException("Ürün stoku null olamaz");
        }
    }

    // Getters
    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Price getPrice() {
        return price;
    }

    public Stock getStock() {
        return stock;
    }

    // Business methods
    public void updateProduct(String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ürün adı null veya boş olamaz");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Ürün açıklaması null veya boş olamaz");
        }

        this.name = name.trim();
        this.description = description.trim();
    }

    public void updatePrice(Price newPrice) {
        if (newPrice == null) {
            throw new IllegalArgumentException("Yeni fiyat null olamaz");
        }
        this.price = newPrice;
    }

    // Stock value object'i ile ilgili işlemler yapılacak.

    public void updateStock(Stock newStock) {
        if (newStock == null) {
            throw new IllegalArgumentException("Yeni stok null olamaz");
        }
        this.stock = newStock;
    }

    public void reduceStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Çıkarılacak miktar pozitif olmalıdır");
        }
        this.stock = this.stock.reduce(quantity);
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Eklenen miktar pozitif olmalıdır");
        }
        this.stock = this.stock.add(quantity);
    }

    public boolean isInStock() {
        return !stock.isOutOfStock();
    }

    public boolean hasEnoughStock(int quantity) {
        return stock.hasEnoughStock(quantity);
    }

    /**
     * Ürünün fiyatını belirli yüzde ile artırır
     */
    public void increasePriceByPercentage(double percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Yüzde negatif olamaz");
        }
        this.price = this.price.increaseByPercentage(new java.math.BigDecimal(percentage));
    }

    /**
     * Ürünün fiyatını belirli yüzde ile azaltır
     */
    public void decreasePriceByPercentage(double percentage) {
        if (percentage < 0) {
            throw new IllegalArgumentException("Yüzde negatif olamaz");
        }
        this.price = this.price.decreaseByPercentage(new java.math.BigDecimal(percentage));
    }

    // equals, hashCode, toString metotlarını override ettik.Java'nın Object
    // sınıfından.

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }

    /**
     * ProductId Value Object - Ürün kimliği ( Bu sefer sınıfın içinde bir sınıf
     * olarak tanımladık.)
     */
    public static class ProductId {
        private final UUID value;

        private ProductId(UUID value) {
            this.value = value;
        }

        public static ProductId generate() {
            return new ProductId(UUID.randomUUID());
        }

        public static ProductId fromString(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Ürün ID'si null veya boş olamaz");
            }
            try {
                return new ProductId(UUID.fromString(id));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Geçersiz ürün ID formatı: " + id);
            }
        }

        public UUID getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            ProductId productId = (ProductId) o;
            return Objects.equals(value, productId.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }
}