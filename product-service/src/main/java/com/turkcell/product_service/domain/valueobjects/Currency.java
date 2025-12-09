package com.turkcell.product_service.domain.valueobjects;

import java.util.Objects;

/**
 * Currency Value Object - DDD'de değer nesnesi
 * Para birimi bilgisini temsil eder ve değişmez (immutable) bir nesnedir
 */
public final class Currency {
    private final String code;
    private final String name;
    private final String symbol;

    // Desteklenen para birimleri
    public static final Currency TRY = new Currency("TRY", "Türk Lirası", "₺");
    public static final Currency USD = new Currency("USD", "Amerikan Doları", "$");
    public static final Currency EUR = new Currency("EUR", "Euro", "€");
    public static final Currency GBP = new Currency("GBP", "İngiliz Sterlini", "£");

    public Currency(String code, String name, String symbol) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Para birimi kodu null veya boş olamaz");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Para birimi adı null veya boş olamaz");
        }
        if (symbol == null || symbol.trim().isEmpty()) {
            throw new IllegalArgumentException("Para birimi sembolü null veya boş olamaz");
        }

        this.code = code.toUpperCase().trim();
        this.name = name.trim();
        this.symbol = symbol.trim();
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    /**
     * Para birimi koduna göre Currency nesnesi oluşturur
     */
    public static Currency fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Para birimi kodu null veya boş olamaz");
        }

        String upperCode = code.toUpperCase().trim();
        switch (upperCode) {
            case "TRY":
                return TRY;
            case "USD":
                return USD;
            case "EUR":
                return EUR;
            case "GBP":
                return GBP;
            default:
                throw new IllegalArgumentException("Desteklenmeyen para birimi: " + code);
        }
    }

    /**
     * Para biriminin desteklenip desteklenmediğini kontrol eder
     */
    public static boolean isSupported(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }

        String upperCode = code.toUpperCase().trim();
        return upperCode.equals("TRY") || upperCode.equals("USD") ||
                upperCode.equals("EUR") || upperCode.equals("GBP");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Currency currency = (Currency) o;
        return Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return code + " - " + name + " (" + symbol + ")";
    }
}