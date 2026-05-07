package io.github.kwyczlinski;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FreeMugOver200Promotion implements Promotion {
    private static final Product GIFT_MUG = new Product("COMP-MUG-OVER-200", "Company Mug", 0.0);
    
    @Override
    public List<Product> apply(List<Product> products) {
        
        if (products == null || products.isEmpty()) return List.of();
        
        List<Product> result = products.stream().filter(Objects::nonNull)
            .collect(Collectors.toCollection(ArrayList::new));

        boolean mugAlreadyAdded = result.stream()
            .anyMatch(p -> p.getCode().equals(GIFT_MUG.getCode()));

        if (result.stream().mapToDouble(Product::getDiscountPrice).sum() <= 200) {
            result.removeIf(p -> p.getCode().equals(GIFT_MUG.getCode()));
            
            return result;
        } 

        if (!mugAlreadyAdded) {
            result.add(GIFT_MUG);
        }

        return result;
    }

    @Override
    public String name() { return "Free mug when basket over 200"; }

}
