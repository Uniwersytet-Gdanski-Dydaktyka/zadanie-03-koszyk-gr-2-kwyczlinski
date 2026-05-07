package io.github.kwyczlinski;

import java.util.List;
import java.util.Objects;

public class DiscountOver300Promotion implements Promotion {

    @Override
    public List<Product> apply(List<Product> products) {
        
        if (products == null || products.isEmpty()) return List.of();

        double total = products.stream().filter(Objects::nonNull).mapToDouble(Product::getDiscountPrice).sum(); 

        if (total <= 300) return products.stream().filter(Objects::nonNull).toList();
        
        return products.stream().filter(Objects::nonNull).map(p -> p.withDiscountPrice(p.getDiscountPrice() * 0.95)).toList();
    }

    @Override
    public String name() { return "5% discount when basket over 300"; }
    
}
