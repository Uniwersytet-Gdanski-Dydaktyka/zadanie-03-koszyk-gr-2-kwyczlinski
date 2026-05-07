package io.github.kwyczlinski;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OneTimeSingleItem30PercentOffPromotion implements Promotion {

    @Override
    public List<Product> apply(List<Product> products) {
        
        if (products == null || products.isEmpty()) return List.of();

        Optional<Product> priciest = products.stream().filter(Objects::nonNull).max(Comparator.comparingDouble(Product::getDiscountPrice));

        if (!priciest.isPresent()) { return products.stream().filter(Objects::nonNull).toList(); }

        return products.stream()
            .filter(Objects::nonNull)
            .map(p -> p == priciest.get() ? p.withDiscountPrice(p.getDiscountPrice() * 0.7) : p)
            .toList();
    }
    
    @Override
    public String name() { return "One time 30% discount on one product"; }

}
