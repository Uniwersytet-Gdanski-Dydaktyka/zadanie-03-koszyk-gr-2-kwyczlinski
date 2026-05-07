package io.github.kwyczlinski;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ThirdProductFreePromotion  implements Promotion {

    @Override
    public List<Product> apply(List<Product> products) {
        
        if (products == null || products.isEmpty()) return List.of();

        if (products.stream().filter(Objects::nonNull).toList().size() < 3) { return products.stream().filter(Objects::nonNull).toList(); }

        Optional<Product> cheapest = products.stream().filter(Objects::nonNull).min(Comparator.comparingDouble(Product::getDiscountPrice));

        if (!cheapest.isPresent()) { return products.stream().filter(Objects::nonNull).toList(); }

        return products.stream().filter(Objects::nonNull).map(p -> p == cheapest.get() ? p.withDiscountPrice(0.0) : p).toList();
    }

    @Override
    public String name() { return "Cheapest product free when buying 3 or more products"; }
    
}
