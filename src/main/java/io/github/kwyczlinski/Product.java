package io.github.kwyczlinski;

// Cechy związane z klasą Product:

// kod produktu (code) - String
// nazwa produktu (name) - String
// cena produktu (price) – double
// cena produktu po uwzględnieniu promocji (discountPrice) - double

public final class Product {
    private final String code;
    private final String name;
    private final double price;
    private final double discountPrice;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = price;
    }

    Product(String code, String name, double price, double discountPrice) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.discountPrice = discountPrice;
    }

    public Product withDiscountPrice(double discountPrice) {
        return new Product(this.code, this.name, this.price, discountPrice);
    }

    public Product resetDiscount() {
        return new Product(this.code, this.name, this.price);
    }

    public String getCode() { return this.code; }

    public String getName() { return this.name; }

    public double getPrice() { return this.price; }

    public double getDiscountPrice() { return this.discountPrice; }

}