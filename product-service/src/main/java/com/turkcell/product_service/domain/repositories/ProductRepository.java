package com.turkcell.product_service.domain.repositories;

import com.turkcell.product_service.domain.entities.Product;
import java.util.List;
import java.util.Optional;

/**
 * ProductRepository Interface - DDD'de repository pattern
 * Domain katmanında tanımlanır, infrastructure katmanında implement edilir
 * Ürün verilerine erişim için soyutlama sağlar
 */
public interface ProductRepository {

    /**
     * Ürünü kaydeder veya günceller
     * 
     * @param product Kaydedilecek ürün
     * @return Kaydedilen ürün
     */
    Product save(Product product);

    /**
     * ID'ye göre ürün bulur
     * 
     * @param id Ürün ID'si
     * @return Bulunan ürün (varsa)
     */
    Optional<Product> findById(Product.ProductId id);

    /**
     * Tüm ürünleri getirir
     * 
     * @return Ürün listesi
     */
    List<Product> findAll();

    /**
     * Ürün adına göre arama yapar
     * 
     * @param name Aranacak ürün adı
     * @return Bulunan ürünler
     */
    List<Product> findByNameContaining(String name);

    /**
     * Stokta olan ürünleri getirir
     * 
     * @return Stokta olan ürünler
     */
    List<Product> findInStockProducts();

    /**
     * Stokta olmayan ürünleri getirir
     * 
     * @return Stokta olmayan ürünler
     */
    List<Product> findOutOfStockProducts();

    /**
     * Belirli fiyat aralığındaki ürünleri getirir
     * 
     * @param minPrice Minimum fiyat
     * @param maxPrice Maksimum fiyat
     * @return Fiyat aralığındaki ürünler
     */
    List<Product> findByPriceRange(double minPrice, double maxPrice);

    /**
     * Ürünü siler
     * 
     * @param id Silinecek ürün ID'si
     */
    void deleteById(Product.ProductId id);

    /**
     * Ürünün var olup olmadığını kontrol eder
     * 
     * @param id Kontrol edilecek ürün ID'si
     * @return Ürün varsa true, yoksa false
     */
    boolean existsById(Product.ProductId id);

    /**
     * Toplam ürün sayısını döner
     * 
     * @return Ürün sayısı
     */
    long count();

    /**
     * Stokta olan ürün sayısını döner
     * 
     * @return Stokta olan ürün sayısı
     */
    long countInStockProducts();
}